import {Component, OnInit} from '@angular/core';
import {ShopCartService} from "../../../_service/shop-cart.service";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  shopCart = [];
  itemOrder;
  totalItemCount = 0;
  totalPrice = 0;

  constructor(private shopCartService: ShopCartService) {
    this.itemOrder = this.shopCartService.getNewItemOrder();
    this.shopCartService.shopCartSub.observers = [];
    this.shopCartService.shopCartSub.subscribe((item) => {
      this.addOrder(item);
    })
    //console.log(this.shopCartService.shopCartSub.observers.length)
  }

  ngOnInit(): void {
    this.initShopCart();
  }

  initShopCart() {
    this.shopCartService.getOrder('U20220102233339').subscribe((itemOrder) => {
      // console.log(itemOrder)
      if (itemOrder !== null && itemOrder.orderId !== undefined) {
        this.itemOrder = itemOrder;
        if (itemOrder.orderDetails !== undefined) {
          this.shopCartService.orderDetails = itemOrder.orderDetails;
          for (let orderDetail of itemOrder.orderDetails) {
            orderDetail.item.orderDetail = JSON.parse(JSON.stringify(orderDetail));
            orderDetail.item.orderDetail.item = {
              itemId: orderDetail.item.itemId,
            };
            this.addToCart(orderDetail.item);
          }
          this.shopCartService.initShopCartSub.next(itemOrder.orderDetails);
        }
      } else {
        this.itemOrder.customerProfile.customerProId = 'U20220102233339';
      }
    })
  }

  addToCart(item) {
    // console.log(item)
    this.totalItemCount += item.orderDetail.quantity;
    this.totalPrice += this.calcDiscount(item);
    let indexShop: any = this.shopCart.findIndex(shopCart => {
      return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
    })
    if (indexShop === -1) {
      this.shopCart.push({
        shop: item.businessProfileCategory.businessProfile,
        itemCount: item.orderDetail.quantity,
        totalPrice: this.calcDiscount(item),
        items: [item]
      });
    } else {
      let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
        return itemObj.itemId === item.itemId
      })
      if (indexItem === -1) {
        this.shopCart[indexShop].items.push(item);
      }
      this.shopCart[indexShop].itemCount += item.orderDetail.quantity;
      this.shopCart[indexShop].totalPrice += this.calcDiscount(item);
    }
    this.shopCartService.shopCartItemsSub.next(item);
  }

  addOrder(item) {
    if (item.itemQty > item.orderDetail.quantity) {
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      orderDetail.item = JSON.parse(JSON.stringify(item));
      this.shopCartService.addOrderDetail(orderDetail).subscribe((orderDetailR) => {
        orderDetail.orderDetailId = orderDetailR.orderDetailId;
        this.itemOrder.orderId = orderDetailR.itemOrder.orderId;
        orderDetail.quantity = orderDetailR.quantity;

        this.addToCart(item)
        let indexOrderDetail: any = this.itemOrder.orderDetails.findIndex(orderDetailObj => {
          return orderDetailObj.orderDetailId === orderDetailR.orderDetailId
        })
        orderDetail.quantity = orderDetailR.quantity;
        if (indexOrderDetail === -1) {
          this.itemOrder.orderDetails.push(orderDetail);
        } else {
          this.itemOrder.orderDetails[indexOrderDetail] = orderDetail;
        }
        console.log(this.itemOrder)
      })
    }
  }

  placeOrder() {
    // console.log(this.itemOrder)
    this.shopCartService.placeOrder(this.itemOrder).subscribe((reply) => {
      for (let orderDetail of this.itemOrder.orderDetails) {
        orderDetail.item.itemQty -= orderDetail.quantity;
        orderDetail.item.orderDetail.quantity = 0;
        this.shopCartService.shopCartItemsSub.next(orderDetail.item);
      }
      this.itemOrder = this.shopCartService.getNewItemOrder();
      this.itemOrder.customerProfile.customerProId = 'U20220102233339';
      this.shopCart = [];
      this.totalItemCount = 0;
      this.totalPrice = 0;
    });
  }

  itemCountInc(shop, item) {
    if (item.itemQty > item.orderDetail.quantity) {
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount++;
        shop.totalPrice += item.itemPrice;
        this.totalPrice += item.itemPrice;
        shop.itemCount++;
        orderDetail.quantity++;
        this.shopCartService.shopCartItemsSub.next(item);
      })
    }
  }

  itemCountDec(shop, item) {
    if (item.orderDetail.quantity > 1) {
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount--;
        shop.totalPrice -= item.itemPrice;
        this.totalPrice -= item.itemPrice;
        shop.itemCount--;
        orderDetail.quantity--;
        this.shopCartService.shopCartItemsSub.next(item);
      })
    }
  }

  removeItem(shopIndex, shopItem, itemIndex?) {
    if (shopItem === 'shop') {
      for (let item of this.shopCart[shopIndex].items) {
        item.itemCount = 0;
        this.shopCartService.shopCartItemsSub.next(item);
      }
      this.shopCart.splice(shopIndex, 1);
    } else {
      this.shopCart[shopIndex].itemCount -= this.shopCart[shopIndex].items[itemIndex].itemCount;
      this.shopCart[shopIndex].items[itemIndex].itemCount = 0;
      this.shopCartService.shopCartItemsSub.next(this.shopCart[shopIndex].items[itemIndex]);
      this.shopCart[shopIndex].items.splice(itemIndex, 1);
    }
    // this.cartDB();
  }

  calcDiscount(item) {
    // console.log(item)
    if (item.itemDiscountType === 'Cash') {
      return (item.itemPrice - item.itemDiscountType) * item.orderDetail.quantity;
    } else if (item.itemDiscountType === 'Percentage') {
      return (item.itemPrice * ((100 - item.itemDiscount) / 100)) * item.orderDetail.quantity;
    }
    return item.itemPrice * item.orderDetail.quantity;
  }
}
