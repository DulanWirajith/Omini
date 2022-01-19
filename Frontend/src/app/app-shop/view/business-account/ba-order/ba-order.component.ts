import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {BusinessAccountService} from "../../../_service/business-account.service";
import {LoginService} from "../../../../_service/login.service";

@Component({
  selector: 'app-ba-order',
  templateUrl: './ba-order.component.html',
  styleUrls: ['./ba-order.component.css']
})
export class BaOrderComponent implements OnInit {

  itemOrders = [];
  pendingItemOrders = [];
  inProgressItemOrders = [];
  completeItemOrders = [];

  constructor(private itemService: ItemService, private businessAccountService: BusinessAccountService, private loginService: LoginService) {
    // this.businessAccountService.navBarSub.subscribe((val) => {
    //   if (val === 'Order') {
    //     this.getItemOrders();
    //   }
    // })
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getItemOrders(businessCategoryId);
    })
  }

  ngOnInit(): void {
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getItemOrders(this.businessAccountService.businessCategory.businessCategoryId);
    }
  }

  getItemOrders(businessCategoryId) {
    // this.itemOrders = this.businessAccountService.itemOrders;
    // this.itemService.getItemOrders('B321', businessCategoryId, 'Pending').subscribe((itemOrders) => {
    //   this.itemOrders = itemOrders;
    // })
    this.itemService.getItemOrders(this.loginService.getUser().userId, businessCategoryId).subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
      this.pendingItemOrders = itemOrders.filter(orderObj => {
        return orderObj.status === 'Pending';
      });
      this.inProgressItemOrders = itemOrders.filter(orderObj => {
        return orderObj.status === 'In Progress';
      });
      this.completeItemOrders = itemOrders.filter(orderObj => {
        return orderObj.status === 'Completed';
      });
      // this.router.navigate(['/shop/header/business_account/ba_order'])
    })
  }

  changeOrderStatus(itemOrder, status, index, orderList) {
    this.itemService.changeOrderStatus(itemOrder.orderId, this.loginService.getUser().userId, this.businessAccountService.businessCategory.businessCategoryId, status).subscribe((itemOrderObj) => {
      orderList.splice(index, 1);
      if (itemOrderObj.status === 'Pending') {
        this.pendingItemOrders.push(itemOrder)
      } else if (itemOrderObj.status === 'In Progress') {
        this.inProgressItemOrders.push(itemOrder)
      } else if (itemOrderObj.status === 'Completed') {
        this.completeItemOrders.push(itemOrder)
      }
      this.loginService.getUser().businessProfile.countPendingOrders = this.pendingItemOrders.length + this.inProgressItemOrders.length;
      // localStorage.setItem('user',)
    })
  }
}
