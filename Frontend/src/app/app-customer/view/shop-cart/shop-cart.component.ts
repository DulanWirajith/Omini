import {Component, OnDestroy, OnInit} from '@angular/core';
import {ShopCartService} from "../../_service/shop-cart.service";
import {LoginService} from "../../../_service/login.service";
import {ItemGService} from "../../../_service/item-g.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit, OnDestroy {

  static lastComp: ShopCartComponent;

  shopCart = [];
  itemOrder;
  totalItemCount = 0;
  totalPrice = 0;

  constructor(private shopCartService: ShopCartService, private loginService: LoginService, private itemServiceG: ItemGService) {
    // console.log(ShopCartComponent.lastComp)
    if (ShopCartComponent.lastComp === undefined) {
      this.itemOrder = this.shopCartService.getNewItemOrder();
      // this.shopCartService.itemOrder = this.itemOrder;
      this.shopCartService.shopCartSub.observers = [];
      this.shopCartService.shopCartSub.subscribe((item) => {
        this.addOrder(item);
      })
    } else {
      return ShopCartComponent.lastComp;
    }
  }

  ngOnInit(): void {
    if (ShopCartComponent.lastComp === undefined) {
      this.initCart();
    }
  }

  ngOnDestroy(): void {
    if (localStorage.getItem('user') !== null) {
      ShopCartComponent.lastComp = this;
    } else {
      ShopCartComponent.lastComp = undefined;
    }
  }

  initCart() {
    // console.log(itemOrder)
    if (this.loginService.getUser() !== null) {
      this.shopCartService.getOrder(this.loginService.getUser().userId).subscribe((itemOrder) => {
        this.shopCart = [];
        this.totalItemCount = 0;
        this.totalPrice = 0;
        if (itemOrder !== null && itemOrder.orderId !== undefined) {
          this.itemOrder = itemOrder;
          // console.log(this.itemOrder)
          if (itemOrder.orderDetails !== undefined) {
            this.shopCartService.orderDetails = itemOrder.orderDetails;
            for (let orderDetail of itemOrder.orderDetails) {
              orderDetail.itemPackage.orderDetail = JSON.parse(JSON.stringify(orderDetail));
              orderDetail.itemPackage.orderDetail.itemPackage = {
                itemPackageId: orderDetail.itemPackage.itemPackageId,
              };
              this.addToCart(orderDetail.itemPackage);
              // }
            }
            this.shopCartService.initShopCartSub.next(itemOrder.orderDetails);
            this.shopCartService.shopCart = this.shopCart;
          }
        } else {
          this.itemOrder.customerProfile.customerProId = this.loginService.getUser().userId;
        }
      })
    }
  }

  addToCart(itemPackage) {
    // console.log(item)
    this.totalItemCount += itemPackage.orderDetail.quantity;
    this.totalPrice += (itemPackage.discountedPrice * itemPackage.orderDetail.quantity);
    let indexShop: any = this.shopCart.findIndex(shopCart => {
      return shopCart.shop.businessProId === itemPackage.businessProfileCategory.businessProfile.businessProId
    })
    if (indexShop === -1) {
      this.shopCart.push({
        shop: itemPackage.businessProfileCategory.businessProfile,
        shopProfile: itemPackage,
        itemCount: itemPackage.orderDetail.quantity,
        totalPrice: (itemPackage.discountedPrice * itemPackage.orderDetail.quantity),
        items: [itemPackage]
      });
    } else {
      let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
        return itemObj.itemPackageId === itemPackage.itemPackageId
      })
      if (indexItem === -1) {
        this.shopCart[indexShop].items.push(itemPackage);
      }
      this.shopCart[indexShop].itemCount += itemPackage.orderDetail.quantity;
      this.shopCart[indexShop].totalPrice += (itemPackage.discountedPrice * itemPackage.orderDetail.quantity);
    }
    this.shopCartService.shopCartItemsSub.next(itemPackage);
    this.shopCartService.shopCart = this.shopCart;
  }

  addOrder(itemPackage) {
    // console.log(itemPackage)
    if (this.loginService.getUser() !== null && this.loginService.getUser().role === 'C') {
      if (itemPackage.orderDetail.quantity === 0) {
        if (itemPackage.quantity > itemPackage.orderDetail.quantity) {
          let orderDetail = itemPackage.orderDetail;
          orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
          orderDetail.makeToOrder = itemPackage.makeToOrder;
          orderDetail.price = itemPackage.discountedPrice;
          orderDetail.itemOrder.orderDetails = [];
          // if (orderDetail.orderDetailType === 'Item') {
          //   orderDetail.item = JSON.parse(JSON.stringify(item));
          // } else if (orderDetail.orderDetailType === 'ItemPackage') {
          orderDetail.itemPackage = JSON.parse(JSON.stringify(itemPackage));
          // }
          orderDetail.businessProfileCategory = itemPackage.businessProfileCategory;
          this.shopCartService.addOrderDetail(orderDetail).subscribe((orderDetailR) => {
            //console.log(orderDetail.orderDetailId)
            orderDetail.orderDetailId = orderDetailR.orderDetailId;
            this.itemOrder.orderId = orderDetailR.itemOrder.orderId;
            orderDetail.quantity = orderDetailR.quantity;
            this.addToCart(itemPackage);
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
      } else if (itemPackage.quantity > itemPackage.orderDetail.quantity) {
        // console.log(item)
        let indexShop: any = this.shopCart.findIndex(shopCart => {
          return shopCart.shop.businessProId === itemPackage.businessProfileCategory.businessProfile.businessProId
        })
        let orderDetail = JSON.parse(JSON.stringify(itemPackage.orderDetail));
        // if (orderDetail.orderDetailType === 'Item') {
        //   orderDetail.item = JSON.parse(JSON.stringify(item));
        // } else if (orderDetail.orderDetailType === 'ItemPackage') {
        orderDetail.itemPackage = JSON.parse(JSON.stringify(itemPackage));
        // }
        orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
        orderDetail.itemOrder.orderDetails = [];
        orderDetail.businessProfileCategory = itemPackage.businessProfileCategory;
        this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
          // console.log(orderDetailR)
          this.totalItemCount++;
          let price = itemPackage.discountedPrice;
          orderDetail.quantity++;

          let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
            return itemObj.itemPackageId === itemPackage.itemPackageId
          })
          this.shopCart[indexShop].items[indexItem].orderDetail.quantity++;
          this.shopCart[indexShop].totalPrice += price;
          this.shopCart[indexShop].itemCount++;
          this.totalPrice += price;
          itemPackage.orderDetail = orderDetail;
          this.shopCartService.shopCartItemsSub.next(itemPackage);
        })
      }
    }
    // console.log(this.shopCart)
  }

  itemCountInc(shop, itemPackage) {
    // console.log(item)
    if (itemPackage.quantity > itemPackage.orderDetail.quantity) {
      let orderDetail = itemPackage.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      orderDetail.businessProfileCategory = itemPackage.businessProfileCategory;
      this.shopCartService.updateOrderDetail('inc', orderDetail).subscribe((orderDetailR) => {
        // console.log(orderDetailR)
        this.totalItemCount++;
        let price = itemPackage.discountedPrice;
        shop.totalPrice += price;
        this.totalPrice += price;
        shop.itemCount++;
        orderDetail.quantity++;
        this.shopCartService.shopCartItemsSub.next(itemPackage);
      })
    }
  }

  itemCountDec(shop, item) {
    if (item.orderDetail.quantity > 1) {
      let orderDetail = item.orderDetail;
      orderDetail.itemOrder = JSON.parse(JSON.stringify(this.itemOrder));
      orderDetail.itemOrder.orderDetails = [];
      orderDetail.businessProfileCategory = item.businessProfileCategory;
      this.shopCartService.updateOrderDetail('dec', orderDetail).subscribe((orderDetailR) => {
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

  placeOrder() {
    // console.log(this.itemOrder)
    if (this.itemOrder.orderId !== '') {
      this.shopCartService.placeOrder(this.itemOrder).subscribe((reply) => {
        for (let orderDetail of this.itemOrder.orderDetails) {
          // if (orderDetail.orderDetailType === 'Item') {
          //   if (!orderDetail.makeToOrder) {
          //     orderDetail.item.quantity -= orderDetail.quantity;
          //   }
          //   orderDetail.item.orderDetail.quantity = 0;
          //   orderDetail.orderDetailId = undefined;
          //   this.shopCartService.shopCartItemsSub.next(orderDetail.item);
          // } else if (orderDetail.orderDetailType === 'ItemPackage') {
          if (!orderDetail.makeToOrder) {
            orderDetail.itemPackage.quantity -= orderDetail.quantity;
          }
          orderDetail.itemPackage.orderDetail.quantity = 0;
          orderDetail.orderDetailId = undefined;
          this.shopCartService.shopCartItemsSub.next(orderDetail.itemPackage);
          // }
        }
        this.itemOrder = this.shopCartService.getNewItemOrder();
        this.itemOrder.customerProfile.customerProId = this.loginService.getUser().userId;
        this.shopCart = [];
        this.totalItemCount = 0;
        this.totalPrice = 0;
        this.setDialogBox('Your cart has been submitted.', true);
      }, (error) => {
        // console.log(error)
        this.setDialogBox(error.error, true);
      });
    }
  }

  // getItemSelected(item) {
  //   this.itemServiceG.itemSub.next({
  //     item: item,
  //     backBtn: 'cart-viewer',
  //     cart: true
  //   });
  // }

  getItemPackageSelected(item) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: item,
      backBtn: 'cart-viewer',
      cart: true
    });
  }

  getUser() {
    return this.loginService.getUser();
  }

  removeCartItem(orderDetailId, indexShop, indexItem) {
    // console.log(this.shopCart[indexShop].items[indexItem])
    this.shopCartService.removeCartItem(orderDetailId).subscribe((reply) => {
      let item = this.shopCart[indexShop].items[indexItem];
      this.totalItemCount -= item.orderDetail.quantity;
      this.totalPrice -= item.orderDetail.price * item.orderDetail.quantity;
      this.shopCart[indexShop].itemCount -= item.orderDetail.quantity;
      this.shopCart[indexShop].totalPrice -= item.orderDetail.price * item.orderDetail.quantity;
      item.orderDetail.quantity = 0;
      this.shopCartService.shopCartItemsSub.next(item);
      if (this.shopCart[indexShop].items.length === 1) {
        if (this.shopCart.length === 1) {
          this.removeCart();
        } else {
          this.shopCart.splice(indexShop, 1);
        }
      } else {
        this.shopCart[indexShop].items.splice(indexItem, 1);
      }
    })
  }

  removeCartShop(shop, indexShop) {
    // console.log(shop)
    let businessProfileCategory = shop.shopProfile.businessProfileCategory;
    this.shopCartService.removeCartShop(this.itemOrder.orderId, businessProfileCategory.businessProfile.businessProId, businessProfileCategory.businessCategory.businessCategoryId).subscribe((reply) => {
      // console.log(3)
      for (let item of this.shopCart[indexShop].items) {
        item.orderDetail.quantity = 0;
        this.shopCartService.shopCartItemsSub.next(item);
      }
      this.totalItemCount -= this.shopCart[indexShop].itemCount
      this.totalPrice -= this.shopCart[indexShop].totalPrice
      if (this.shopCart.length === 1) {
        this.removeCart()
      } else {
        this.shopCart.splice(indexShop, 1);
      }
    })
  }

  removeCart() {
    if (this.itemOrder.orderId !== '') {
      this.shopCartService.removeCart(this.itemOrder.orderId).subscribe((reply) => {
        for (let shop of this.shopCart) {
          for (let item of shop.items) {
            item.orderDetail.quantity = 0;
            this.shopCartService.shopCartItemsSub.next(item);
          }
        }
        this.itemOrder = this.shopCartService.getNewItemOrder();
        this.itemOrder.customerProfile.customerProId = this.loginService.getUser().userId;
        this.shopCart = [];
        this.totalItemCount = 0;
        this.totalPrice = 0;
        this.setDialogBox('Your cart has been cleared.', true);
      })
    }
  }

  confirmation = {
    reply: false,
    messageType: '',
    message: ''
  };

  // confirmationSub = new Subject();

  setDialogBox(message, reply = false, messageType = '') {
    this.confirmation.messageType = messageType;
    this.confirmation.reply = reply;
    this.confirmation.message = message;
  }
}
