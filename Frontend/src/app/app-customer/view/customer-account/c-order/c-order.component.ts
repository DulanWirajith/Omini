import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {ShopCartService} from "../../../_service/shop-cart.service";
import {LoginService} from "../../../../_service/login.service";

@Component({
  selector: 'app-c-order',
  templateUrl: './c-order.component.html',
  styleUrls: ['./c-order.component.css']
})
export class COrderComponent implements OnInit {

  itemOrders = [];
  shopItemOrders = [];

  constructor(private itemService: ItemService, private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.getPendingCustomerOrders();
  }

  getPendingCustomerOrders() {
    this.itemService.getCustomerOrders(this.loginService.getUser().userId).subscribe((itemOrders) => {
      // this.itemOrders = itemOrders;
      for (let i = 0; i < itemOrders.length; i++) {
        console.log(1)
        for (let orderDetail of itemOrders[i].orderDetails) {
          this.addToCart(itemOrders[i], orderDetail, i)
        }
      }
    })
  }

  addToCart(itemOrder, orderDetail, index) {
    if (this.shopItemOrders[index] === undefined) {
      this.shopItemOrders.push({
        itemOrder: itemOrder,
        shops: [{
          shop: orderDetail.businessProfileCategory.businessProfile,
          itemCount: orderDetail.quantity,
          totalPrice: (orderDetail.price * orderDetail.quantity),
          items: [orderDetail],
          expand: false
        }]
      })
    } else {
      // console.log(item)
      // this.totalItemCount += itemOrder.orderDetail.quantity;
      // this.totalPrice += (itemOrder.discountedPrice * itemOrder.orderDetail.quantity);
      let indexShop: any = this.shopItemOrders[index].shops.findIndex(orderDetailObj => {
        // if (orderDetailObj.shop === undefined) {
        //   return false;
        // }
        return orderDetailObj.shop.businessProId === orderDetail.businessProfileCategory.businessProfile.businessProId;
      });
      // console.log(indexShop)
      // if (indexShop === -1) {
      //   // this.shopItemOrders.push(itemOrder)
      //   this.shopItemOrders.push({
      //     itemOrder: itemOrder,
      //     shops: [{
      //       shop: orderDetail.businessProfileCategory.businessProfile,
      //       itemCount: orderDetail.orderDetail.quantity,
      //       totalPrice: (orderDetail.discountedPrice * orderDetail.orderDetail.quantity),
      //       items: [orderDetail]
      //     }]
      //   })
      // } else {
      let indexItem: any = this.shopItemOrders[index].shops[indexShop].items.findIndex(itemObj => {
        return itemObj.orderDetailId === orderDetail.orderDetailId
      })
      if (indexItem === -1) {
        this.shopItemOrders[index].shops[indexShop].items.push(orderDetail);
      }
      this.shopItemOrders[index].shops[indexShop].itemCount += orderDetail.quantity;
      // item.orderDetail.quantity++;
      this.shopItemOrders[index].shops[indexShop].totalPrice += (orderDetail.price * orderDetail.quantity);
      // }
    }
    console.log(this.shopItemOrders)
    // this.shopCartService.shopCartItemsSub.next(orderDetail);
  }
}
