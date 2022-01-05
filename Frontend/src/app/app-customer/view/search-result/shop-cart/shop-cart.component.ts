import {Component, OnInit} from '@angular/core';
import {ShopCartService} from "../../../_service/shop-cart.service";

@Component({
  selector: 'app-shop-cart',
  templateUrl: './shop-cart.component.html',
  styleUrls: ['./shop-cart.component.css']
})
export class ShopCartComponent implements OnInit {

  shopCart = [];

  constructor(private shopCartService: ShopCartService) {
    // console.log(this.shopCartService.shopCartSub.observers.length)
    // this.shopCart = [];
    // if (this.shopCartService.shopCartSub.observers.length === 0) {
    this.shopCartService.shopCartSub.observers = [];
    this.shopCartService.shopCartSub.subscribe((item) => {
      this.addToCart(item);
    })
    // }
  }

  ngOnInit(): void {
    // console.log(7)
    this.shopCart = this.shopCartService.shopCart;
  }

  addToCart(item) {
    if (item.itemQty > item.itemCount) {
      // console.log(item.businessProfileCategory.businessProfile.businessProId)
      let indexShop: any = this.shopCart.findIndex(shopCart => {
        return shopCart.shop.businessProId === item.businessProfileCategory.businessProfile.businessProId
      })
      if (indexShop === -1) {
        item.itemCount = 1;
        this.shopCart.push({
          shop: item.businessProfileCategory.businessProfile,
          items: [item]
        });
      } else {
        let indexItem: any = this.shopCart[indexShop].items.findIndex(itemObj => {
          return itemObj.itemId === item.itemId
        })
        if (indexItem === -1) {
          item.itemCount = 1;
          this.shopCart[indexShop].items.push(item);
        } else {
          this.shopCart[indexShop].items[indexItem].itemCount++;
          this.shopCartService.shopCartItemsSub.next(this.shopCart[indexShop].items[indexItem])
        }
      }
      this.shopCartService.shopCart = this.shopCart;
      console.log(this.shopCart)
    }
    // console.log(this.shopCart.length)
  }

  itemCountInc(item) {
    if (item.itemQty > item.itemCount) {
      item.itemCount++;
      this.shopCartService.shopCartItemsSub.next(item);
    }
  }

  itemCountDec(item) {
    if (item.itemCount > 1) {
      item.itemCount--;
      this.shopCartService.shopCartItemsSub.next(item);
    }
  }
}
