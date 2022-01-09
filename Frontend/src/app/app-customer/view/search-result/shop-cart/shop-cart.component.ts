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

  // businessProfile;

  constructor(private shopCartService: ShopCartService) {
    // this.shopCart = [];
    // if (this.shopCartService.shopCartSub.observers.length === 0) {
    this.itemOrder = this.shopCartService.getNewItemOrder();
    this.shopCartService.shopCartSub.observers = [];
    this.shopCartService.shopCartSub.subscribe((item) => {
      this.addOrder(item);
    })
    //console.log(this.shopCartService.shopCartSub.observers.length)
    // }
  }

  ngOnInit(): void {
    // console.log(7)
    this.initShopCart();
  }

  initShopCart() {
    // this.shopCart = this.shopCartService.shopCart;
    // if (this.shopCart.length === 0) {
    this.shopCartService.getOrder('U20220102233339').subscribe((itemOrder) => {
      // console.log(itemOrder)
      if (itemOrder !== null && itemOrder.orderId !== undefined) {
        // console.log(itemOrder.orderDetails)
        this.itemOrder = itemOrder;
        if (itemOrder.orderDetails !== undefined) {
          this.shopCartService.orderDetails = itemOrder.orderDetails;
          for (let orderDetail of itemOrder.orderDetails) {
            // orderDetail.item.itemCount = orderDetail.quantity;
            orderDetail.item.orderDetail = JSON.parse(JSON.stringify(orderDetail));
            orderDetail.item.orderDetail.item = {
              itemId: orderDetail.item.itemId,
            };
            // orderDetail.item.orderDetail.itemOrder = {
            //   orderId: orderDetail.itemOrder.orderId,
            // };
            // orderDetail.item.orderDetail.item.orderDetail.item = undefined;
            this.addToCart(orderDetail.item);
          }
          this.shopCartService.initShopCartSub.next(itemOrder.orderDetails);
        }
      } else {
        // this.itemOrder = this.shopCartService.getNewItemOrder();
        this.itemOrder.customerProfile.customerProId = 'U20220102233339';
      }

      // if (shopCart != null) {
      //   this.shopCart = JSON.parse(shopCart.cartTxt);
      //   this.shopCartService.shopCart = this.shopCart;
      //   this.shopCartService.initShopCartSub.next(this.shopCart)
      // }
    })
    // }
  }

  addToCart(item) {
    // let item = orderDetail.item;
    console.log(item)
    // if (item.itemQty > item.itemCount) {
    // console.log(item.businessProfileCategory.businessProfile.businessProId)
    this.totalItemCount += item.orderDetail.quantity;
    this.totalPrice += this.calcDiscount(item);
    let indexShop: any = this.shopCart.findIndex(shopCart => {
      return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
    })
    if (indexShop === -1) {
      // item.itemCount = 1;
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
        // item.itemCount = 1;
        this.shopCart[indexShop].items.push(item);
        // this.shopCart[indexShop].itemCount++;
      } else {
        // this.shopCart[indexShop].items[indexItem].itemCount++;
        // this.shopCart[indexShop].itemCount++;
        // this.shopCartService.shopCartItemsSub.next(this.shopCart[indexShop].items[indexItem])
      }
      this.shopCart[indexShop].itemCount += item.orderDetail.quantity;
      this.shopCart[indexShop].totalPrice += this.calcDiscount(item);
    }
    this.shopCartService.shopCartItemsSub.next(item);
    // this.shopCartService.shopCart = this.shopCart;
    // this.cartDB();
    //console.log(this.shopCart)
    // }
    // console.log(this.shopCart.length)
  }

  itemCountInc(shop, item) {
    if (item.itemQty > item.orderDetail.quantity) {
      // item.itemCount++;
      let orderDetail = item.orderDetail;
      // orderDetail.orderDetailId = item.orderDetailId;
      // orderDetail.item = item;
      // orderDetail.quantity = item.itemCount;
      orderDetail.itemOrder = {orderId: this.itemOrder.orderId};
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount++;
        shop.totalPrice += item.itemPrice;
        this.totalPrice += item.itemPrice;
        shop.itemCount++;
        orderDetail.quantity++;
        let indexOrderDetail: any = this.itemOrder.orderDetails.findIndex(orderDetailObj => {
          return orderDetailObj.orderDetailId === orderDetailR.orderDetailId
        })
        this.itemOrder.orderDetails[indexOrderDetail] = orderDetailR;
        this.shopCartService.shopCartItemsSub.next(item);
      })

      // this.cartDB();
    }
  }

  itemCountDec(shop, item) {
    if (item.orderDetail.quantity > 1) {
      let orderDetail = item.orderDetail;
      // orderDetail.orderDetailId = item.orderDetailId;
      // orderDetail.item = item;
      // orderDetail.quantity = item.itemCount;
      orderDetail.itemOrder = {orderId: this.itemOrder.orderId};
      this.shopCartService.updateOrderDetail('dec', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount--;
        shop.totalPrice -= item.itemPrice;
        this.totalPrice -= item.itemPrice;
        shop.itemCount--;
        orderDetail.quantity--;
        let indexOrderDetail: any = this.itemOrder.orderDetails.findIndex(orderDetailObj => {
          return orderDetailObj.orderDetailId === orderDetailR.orderDetailId
        })
        this.itemOrder.orderDetails[indexOrderDetail] = orderDetailR;
        this.shopCartService.shopCartItemsSub.next(item);
      })

      // this.cartDB();
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

  // cartDB() {
  //   this.shopCartService.addCart({
  //     cartTxt: JSON.stringify(this.shopCart),
  //     cartId: 'U20220102233339'
  //   }).subscribe();
  // }

  addOrder(item) {
    if (item.itemQty > item.orderDetail.quantity) {
      // this.businessProfile = item.businessProfileCategory.businessProfile;
      // let shopCart = this.shopCartService.getNewItemOrder();
      // shopCart.amount = 5;
      // shopCart.customerProfile.customerProId = 'U20220102233339';
      // if (this.itemOrder.orderId === '') {
      // let businessProfile = item.businessProfileCategory;
      // let orderDetail = this.shopCartService.getNewOrderDetail();
      // orderDetail.orderDetailId = '1';
      // orderDetail.orderDetailId = item.orderDetailId;
      // orderDetail.item = item;
      // orderDetail.quantity = item.itemCount;
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = this.itemOrder;
      // orderDetail.item = {
      //   itemId: orderDetail.item.itemId,
      // };
      // orderDetail.item.orderDetail.quantity = orderDetailR.quantity;
      orderDetail.item = {
        itemId: item.itemId,
      };
      // this.itemOrder.orderDetails.push(orderDetail);
      //console.log(orderDetail)
      this.shopCartService.addOrderDetail(orderDetail).subscribe((orderDetailR) => {
        item.orderDetailId = orderDetailR.orderDetailId;
        this.itemOrder.orderId = orderDetailR.itemOrder.orderId;
        // orderDetailR.item = item;
        // orderDetail.item.itemCount = orderDetailR.quantity;
        // orderDetailR.item.businessProfile = businessProfile;
        // orderDetail.item.orderDetail = JSON.parse(JSON.stringify(orderDetail));
        orderDetail.quantity = orderDetailR.quantity;
        // orderDetail.item.orderDetail.item = {
        //   itemId: orderDetail.item.itemId,
        // };
        this.addToCart(item)
        let indexOrderDetail: any = this.itemOrder.orderDetails.findIndex(orderDetailObj => {
          return orderDetailObj.orderDetailId === orderDetailR.orderDetailId
        })
        if (indexOrderDetail === -1) {
          this.itemOrder.orderDetails.push(orderDetailR);
        } else {
          this.itemOrder.orderDetails[indexOrderDetail] = orderDetailR;
        }
      })
      // } else {
      //
      // }
    }
  }

  placeOrder() {
    console.log(this.itemOrder)
    // for(let orderDetail of this.itemOrder.orderDetails){
    //   orderDetail
    // }
    this.shopCartService.placeOrder(this.itemOrder).subscribe((reply) => {
      for (let orderDetail of this.itemOrder.orderDetails) {
        orderDetail.item.itemQty -= orderDetail.quantity;
        this.shopCartService.shopCartItemsSub.next(orderDetail.item);
      }
      this.shopCart = [];
      this.totalItemCount = 0;
      this.totalPrice = 0;
    });
  }

  updateItem(item) {

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
