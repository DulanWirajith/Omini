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

  // businessProfile;

  constructor(private shopCartService: ShopCartService) {
    // this.shopCart = [];
    // if (this.shopCartService.shopCartSub.observers.length === 0) {
    this.shopCartService.shopCartSub.observers = [];
    this.shopCartService.shopCartSub.subscribe((item) => {
      this.addOrder(item);
    })
    //console.log(this.shopCartService.shopCartSub.observers.length)
    // }
  }

  ngOnInit(): void {
    console.log(7)
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
            orderDetail.item.itemCount = orderDetail.quantity;
            this.addToCart(orderDetail.item);
          }
          this.shopCartService.initShopCartSub.next(itemOrder.orderDetails);
        }
      } else {
        this.itemOrder = this.shopCartService.getNewItemOrder();
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
    // console.log(item)
    // if (item.itemQty > item.itemCount) {
    // console.log(item.businessProfileCategory.businessProfile.businessProId)
    let indexShop: any = this.shopCart.findIndex(shopCart => {
      return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
    })
    if (indexShop === -1) {
      // item.itemCount = 1;
      this.shopCart.push({
        shop: item.businessProfileCategory.businessProfile,
        itemCount: item.itemCount,
        items: [item]
      });
    } else {
      let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
        return itemObj.itemId === item.itemId
      })
      if (indexItem === -1) {
        // item.itemCount = 1;
        this.shopCart[indexShop].items.push(item);
        this.shopCart[indexShop].itemCount++;
      } else {
        this.shopCart[indexShop].items[indexItem].itemCount++;
        this.shopCart[indexShop].itemCount++;
        // this.shopCartService.shopCartItemsSub.next(this.shopCart[indexShop].items[indexItem])
      }
    }
    this.shopCartService.shopCartItemsSub.next(item);
    // this.shopCartService.shopCart = this.shopCart;
    // this.cartDB();
    //console.log(this.shopCart)
    // }
    // console.log(this.shopCart.length)
  }

  itemCountInc(shop, item) {
    if (item.itemQty > item.itemCount) {
      shop.itemCount++;
      item.itemCount++;
      let orderDetail = this.shopCartService.getNewOrderDetail();
      orderDetail.item = item;
      orderDetail.quantity = item.itemCount;
      orderDetail.itemOrder = this.itemOrder;
      this.shopCartService.updateOrderDetail(orderDetail).subscribe((orderDetailR) => {
        item.itemCount = orderDetailR.quantity;
        this.shopCartService.shopCartItemsSub.next(item);
      })

      // this.cartDB();
    }
  }

  itemCountDec(shop, item) {
    if (item.itemCount > 1) {
      shop.itemCount--;
      item.itemCount--;
      let orderDetail = this.shopCartService.getNewOrderDetail();
      orderDetail.item = item;
      orderDetail.quantity = item.itemCount;
      orderDetail.itemOrder = this.itemOrder;
      this.shopCartService.updateOrderDetail(orderDetail).subscribe((orderDetailR) => {
        item.itemCount = orderDetailR.quantity;
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
    if (item.itemQty > item.itemCount) {
      // this.businessProfile = item.businessProfileCategory.businessProfile;
      // let shopCart = this.shopCartService.getNewItemOrder();
      // shopCart.amount = 5;
      // shopCart.customerProfile.customerProId = 'U20220102233339';
      // if (this.itemOrder.orderId === '') {
      let businessProfile = item.businessProfileCategory;
      let orderDetail = this.shopCartService.getNewOrderDetail();
      // orderDetail.orderDetailId = '1';
      // orderDetail.orderDetailId = item.orderDetailId;
      orderDetail.item = item;
      orderDetail.quantity = item.itemCount;
      orderDetail.itemOrder = this.itemOrder;
      // this.itemOrder.orderDetails.push(orderDetail);
      this.shopCartService.addOrderDetail(orderDetail).subscribe((orderDetail) => {
        orderDetail.item = item;
        orderDetail.item.itemCount = orderDetail.quantity;
        orderDetail.item.businessProfile = businessProfile;
        console.log(orderDetail)
        this.addToCart(orderDetail.item)
      })
      // } else {
      //
      // }
    }
  }

  updateItem(item) {

  }

  calcDiscount(item) {
    if (item.itemDiscountType === 'Cash') {
      return (item.itemPrice - item.itemDiscountType) * item.itemCount;
    } else if (item.itemDiscountType === 'Percentage') {
      return (item.itemPrice * ((100 - item.itemDiscount) / 100)) * item.itemCount;
    }
    return '';
  }
}
