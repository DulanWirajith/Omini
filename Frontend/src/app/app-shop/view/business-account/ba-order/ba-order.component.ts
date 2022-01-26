import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {BusinessAccountService} from "../../../_service/business-account.service";
import {LoginService} from "../../../../_service/login.service";
import {DatePipe} from "@angular/common";
import {Subject} from "rxjs";

@Component({
  selector: 'app-ba-order',
  templateUrl: './ba-order.component.html',
  styleUrls: ['./ba-order.component.css'],
  providers: [DatePipe]
})
export class BaOrderComponent implements OnInit {

  itemOrders = [];
  pendingItemOrders = [];
  inProgressItemOrders = [];
  completeItemOrders = [];
  canceledItemOrders = [];

  confMessage = '';
  confirmationSub = new Subject();

  constructor(private itemService: ItemService, private businessAccountService: BusinessAccountService, private loginService: LoginService, private datePipe: DatePipe) {
    // this.businessAccountService.navBarSub.subscribe((val) => {
    //   if (val === 'Order') {
    //     this.getItemOrders();
    //   }
    // })
    this.businessAccountService.businessCategorySub.observers = [];
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getItemOrders(businessCategoryId, 'Pending', this.getPreDate(), this.getCurDate());
    })
  }

  ngOnInit(): void {
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getItemOrders(this.businessAccountService.businessCategory.businessCategoryId, 'Pending', this.getPreDate(), this.getCurDate());
    }
  }

  updateItemOrders(status, from?, to?) {
    this.getItemOrders(this.businessAccountService.businessCategory.businessCategoryId, status, from, to);
  }

  getItemOrders(businessCategoryId, status, from?, to?) {
    // this.itemOrders = this.businessAccountService.itemOrders;
    // this.itemService.getItemOrders('B321', businessCategoryId, 'Pending').subscribe((itemOrders) => {
    //   this.itemOrders = itemOrders;
    // })
    this.itemService.getItemOrders(this.loginService.getUser().userId, businessCategoryId, status, from, to).subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
      if (status === 'Pending' || status === 'In Progress') {
        this.pendingItemOrders = itemOrders.filter(orderObj => {
          return orderObj.status === 'Pending';
        });
        this.inProgressItemOrders = itemOrders.filter(orderObj => {
          return orderObj.status === 'In Progress';
        });
        this.loginService.getUser().businessProfile.countPendingOrders = this.pendingItemOrders.length + this.inProgressItemOrders.length;
      }
      if (status === 'Completed') {
        this.completeItemOrders = itemOrders.filter(orderObj => {
          return orderObj.status === 'Completed';
        });
      }
      if (status === 'Canceled') {
        this.canceledItemOrders = itemOrders.filter(orderObj => {
          return orderObj.status === 'Canceled';
        });
      }
      // this.router.navigate(['/shop/header/business_account/ba_order'])
    })
  }

  changeOrderStatus(itemOrder, status, index, orderList, message) {
    this.confMessage = message;
    this.confirmationSub.observers = [];
    this.confirmationSub.subscribe(() => {
      this.itemService.changeOrderStatus(itemOrder.orderId, this.loginService.getUser().userId, this.businessAccountService.businessCategory.businessCategoryId, status).subscribe((itemOrderObj) => {
        orderList.splice(index, 1);
        for (let orderDetail of itemOrder.orderDetails) {
          orderDetail.status = itemOrderObj.status;
        }
        if (itemOrderObj.status === 'Pending') {
          this.pendingItemOrders.push(itemOrder)
        } else if (itemOrderObj.status === 'In Progress') {
          this.inProgressItemOrders.push(itemOrder)
        } else if (itemOrderObj.status === 'Completed') {
          this.completeItemOrders.push(itemOrder)
        }
        this.loginService.getUser().businessProfile.countPendingOrders = this.pendingItemOrders.length + this.inProgressItemOrders.length;
        localStorage.setItem('user', JSON.stringify(this.loginService.getUser()))
      })
    })
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
