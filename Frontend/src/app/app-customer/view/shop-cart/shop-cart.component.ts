import {Component, OnInit} from '@angular/core';
import {ShopCartService} from "../../_service/shop-cart.service";
import {LoginService} from "../../../_service/login.service";
import {ItemGService} from "../../../_service/item-g.service";

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

  constructor(private shopCartService: ShopCartService, private loginService: LoginService, private itemServiceG: ItemGService) {
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
    if (this.loginService.getUser() !== null && this.loginService.getUser().role === 'C') {
      this.shopCartService.getOrder(this.loginService.getUser().userId).subscribe((itemOrder) => {
        // console.log(itemOrder)
        if (itemOrder !== null && itemOrder.orderId !== undefined) {
          this.itemOrder = itemOrder;
          if (itemOrder.orderDetails !== undefined) {
            this.shopCartService.orderDetails = itemOrder.orderDetails;
            for (let orderDetail of itemOrder.orderDetails) {
              if (orderDetail.orderDetailType === 'Item') {
                orderDetail.item.orderDetail = JSON.parse(JSON.stringify(orderDetail));
                orderDetail.item.orderDetail.item = {
                  itemId: orderDetail.item.itemId,
                };
                this.addToCart(orderDetail.item);
              } else if (orderDetail.orderDetailType === 'ItemPackage') {
                orderDetail.itemPackage.orderDetail = JSON.parse(JSON.stringify(orderDetail));
                orderDetail.itemPackage.orderDetail.itemPackage = {
                  itemPackageId: orderDetail.itemPackage.itemPackageId,
                };
                this.addToCart(orderDetail.itemPackage);
              }
            }
            this.shopCartService.initShopCartSub.next(itemOrder.orderDetails);
          }
        } else {
          this.itemOrder.customerProfile.customerProId = this.loginService.getUser().userId;
        }
      })
    } else {

    }
  }

  addToCart(item) {
    // console.log(item)
    this.totalItemCount += item.orderDetail.quantity;
    this.totalPrice += (item.discountedPrice * item.orderDetail.quantity);
    let indexShop: any = this.shopCart.findIndex(shopCart => {
      return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
    })
    if (indexShop === -1) {
      this.shopCart.push({
        shop: item.businessProfileCategory.businessProfile,
        itemCount: item.orderDetail.quantity,
        totalPrice: (item.discountedPrice * item.orderDetail.quantity),
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
      // item.orderDetail.quantity++;
      this.shopCart[indexShop].totalPrice += (item.discountedPrice * item.orderDetail.quantity);
    }
    this.shopCartService.shopCartItemsSub.next(item);
  }

  addOrder(item) {
    // console.log(item)
    if (this.loginService.getUser() !== null && this.loginService.getUser().role === 'C') {
      if (item.quantity === -1 || item.quantity > item.orderDetail.quantity) {
        let orderDetail = item.orderDetail;
        orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
        orderDetail.price = item.discountedPrice;
        orderDetail.itemOrder.orderDetails = [];
        if (orderDetail.orderDetailType === 'Item') {
          orderDetail.item = JSON.parse(JSON.stringify(item));
        } else if (orderDetail.orderDetailType === 'ItemPackage') {
          orderDetail.itemPackage = JSON.parse(JSON.stringify(item));
        }
        orderDetail.businessProfileCategory = item.businessProfileCategory;
        //   businessProfile:{
        //     businessProId:'B321',
        //     businessCategory:{
        //       businessCategoryId:this.bu
        //     }
        //   }
        // }
        this.shopCartService.addOrderDetail(orderDetail).subscribe((orderDetailR) => {
          //console.log(orderDetail.orderDetailId)
          orderDetail.orderDetailId = orderDetailR.orderDetailId;
          this.itemOrder.orderId = orderDetailR.itemOrder.orderId;
          if (orderDetail.quantity === 0) {
            // if (item.quantity === -1) {
            //   orderDetail.quantity = -1;
            // } else {
            orderDetail.quantity = orderDetailR.quantity;
            this.addToCart(item);
          } else {
            // console.log(55)
            //console.log(this.shopCart)
            let indexShop: any = this.shopCart.findIndex(shopCart => {
              return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
            })
            let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
              return itemObj.itemId === item.itemId
            })
            let price = item.discountedPrice;
            this.shopCart[indexShop].items[indexItem].orderDetail.quantity++;
            // this.shopCart[indexShop].items[indexItem].price += price;
            this.totalItemCount++;
            this.shopCart[indexShop].totalPrice += price;
            this.shopCart[indexShop].itemCount++;
            // item.price += price;
            // shop.totalPrice += item.price;
            this.totalPrice += price;
            // shop.itemCount++;
          }
          // }
          let indexOrderDetail: any = this.itemOrder.orderDetails.findIndex(orderDetailObj => {
            return orderDetailObj.orderDetailId === orderDetailR.orderDetailId
          })
          orderDetail.quantity = orderDetailR.quantity;
          if (indexOrderDetail === -1) {
            this.itemOrder.orderDetails.push(orderDetail);
          } else {
            this.itemOrder.orderDetails[indexOrderDetail] = orderDetail;
          }
          // console.log(this.itemOrder)
        })
      }
    } else {

    }
    // console.log(item)
  }

  placeOrder() {
    // console.log(this.itemOrder)
    this.shopCartService.placeOrder(this.itemOrder).subscribe((reply) => {
      for (let orderDetail of this.itemOrder.orderDetails) {
        if (orderDetail.orderDetailType === 'Item') {
          orderDetail.item.quantity -= orderDetail.quantity;
          orderDetail.item.orderDetail.quantity = 0;
          orderDetail.orderDetailId = undefined;
          this.shopCartService.shopCartItemsSub.next(orderDetail.item);
        } else if (orderDetail.orderDetailType === 'ItemPackage') {
          // orderDetail.itemPackage.quantity -= orderDetail.quantity;
          orderDetail.itemPackage.orderDetail.quantity = 0;
          orderDetail.orderDetailId = undefined;
          this.shopCartService.shopCartItemsSub.next(orderDetail.itemPackage);
        }
      }
      this.itemOrder = this.shopCartService.getNewItemOrder();
      this.itemOrder.customerProfile.customerProId = this.loginService.getUser().userId;
      this.shopCart = [];
      this.totalItemCount = 0;
      this.totalPrice = 0;
    });
  }

  itemCountInc(shop, item) {
    if (item.quantity === -1 || item.quantity > item.orderDetail.quantity) {
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      orderDetail.businessProfileCategory = item.businessProfileCategory;
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount++;
        let price = item.discountedPrice;
        shop.totalPrice += price;
        this.totalPrice += price;
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
      orderDetail.businessProfileCategory = item.businessProfileCategory;
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount--;
        let price = item.discountedPrice;
        shop.totalPrice -= price;
        this.totalPrice -= price;
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

  getItemSelected(item) {
    this.itemServiceG.itemSub.next({
      item: item,
      backBtn: 'cart-viewer'
    });
  }

  getItemPackageSelected(item) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: item,
      backBtn: 'cart-viewer'
    });
  }

  getUser() {
    return this.loginService.getUser();
  }

  // calcDiscount(item, calcForOne = false) {
  //   // console.log(item)
  //   if (calcForOne) {
  //     if (item.discountType === 'Cash') {
  //       return (item.price - item.discount);
  //     } else if (item.discountType === 'Percentage') {
  //       return (item.price * ((100 - item.discount) / 100));
  //     }
  //     return item.price;
  //   } else {
  //     if (item.discountType === 'Cash') {
  //       return (item.price - item.discount) * item.orderDetail.quantity;
  //     } else if (item.discountType === 'Percentage') {
  //       return (item.price * ((100 - item.discount) / 100)) * item.orderDetail.quantity;
  //     }
  //     return item.price * item.orderDetail.quantity;
  //   }
  // }
}
