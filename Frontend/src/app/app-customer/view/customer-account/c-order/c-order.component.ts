import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {ShopCartService} from "../../../_service/shop-cart.service";
import {LoginService} from "../../../../_service/login.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-c-order',
  templateUrl: './c-order.component.html',
  styleUrls: ['./c-order.component.css'],
  providers: [DatePipe]
})
export class COrderComponent implements OnInit {

  itemOrders = [];
  shopItemOrders = [];
  pendingOrders = [];
  completedOrders = [];
  canceledOrders = [];

  constructor(private itemService: ItemService, private loginService: LoginService, private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    this.getCustomerOrders('Pending', this.getPreDate(), this.getCurDate());
  }

  getCustomerOrders(status, from, to) {
    this.itemService.getCustomerOrders(this.loginService.getUser().userId, status, from, to).subscribe((itemOrders) => {
      // this.itemOrders = itemOrders;
      for (let i = 0; i < itemOrders.length; i++) {
        // console.log(1)
        for (let orderDetail of itemOrders[i].orderDetails) {
          this.divideToShops(itemOrders[i], orderDetail, i)
        }
      }
      if (status === 'Pending') {
        for (let shopItemOrder of this.shopItemOrders) {
          for (let shop of shopItemOrder.shops) {
            if (shop.status === 'Pending' || shop.status === 'In Progress') {
              shopItemOrder.itemOrder.status = 'Pending';
            }
          }
        }
        this.pendingOrders = this.shopItemOrders.filter(shopItemOrder => {
          return shopItemOrder.itemOrder.status === 'Pending';
        });
      }
      if (status === 'Completed') {
        this.completedOrders = this.shopItemOrders.filter(shopItemOrder => {
          return shopItemOrder.itemOrder.status === 'Completed';
        });
      }
      if (status === 'Canceled') {
        this.canceledOrders = this.shopItemOrders.filter(shopItemOrder => {
          return shopItemOrder.itemOrder.status === 'Canceled';
        });
      }
    })
  }

  divideToShops(itemOrder, orderDetail, index) {
    if (this.shopItemOrders[index] === undefined) {
      this.shopItemOrders.push({
        itemOrder: itemOrder,
        shops: [{
          shop: orderDetail.businessProfileCategory.businessProfile,
          status: orderDetail.status,
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
      if (indexShop === -1) {
        this.shopItemOrders[index].shops.push({
          shop: orderDetail.businessProfileCategory.businessProfile,
          status: orderDetail.status,
          itemCount: orderDetail.quantity,
          totalPrice: (orderDetail.price * orderDetail.quantity),
          items: [orderDetail],
          expand: false
        })
        //   this.shopItemOrders.push({
        //     itemOrder: itemOrder,
        //     shops: [{
        //       shop: orderDetail.businessProfileCategory.businessProfile,
        //       itemCount: orderDetail.orderDetail.quantity,
        //       totalPrice: (orderDetail.discountedPrice * orderDetail.orderDetail.quantity),
        //       items: [orderDetail]
        //     }]
        //   })
        // }
      } else {
        let indexItem: any = this.shopItemOrders[index].shops[indexShop].items.findIndex(itemObj => {
          return itemObj.orderDetailId === orderDetail.orderDetailId
        })
        if (indexItem === -1) {
          this.shopItemOrders[index].shops[indexShop].items.push(orderDetail);
        }
        this.shopItemOrders[index].shops[indexShop].itemCount += orderDetail.quantity;
        // item.orderDetail.quantity++;
        this.shopItemOrders[index].shops[indexShop].totalPrice += (orderDetail.price * orderDetail.quantity);
      }
    }
    // console.log(this.shopItemOrders)
    // this.shopCartService.shopCartItemsSub.next(orderDetail);
  }

  getUser() {
    return this.loginService.getUser();
  }

  getPreDate() {
    let date = new Date();
    date.setDate(date.getDate() - 7);
    return this.datePipe.transform(date, 'yyyy-MM-dd')
  }

  getCurDate() {
    return this.datePipe.transform(new Date(), 'yyyy-MM-dd')
  }
}
