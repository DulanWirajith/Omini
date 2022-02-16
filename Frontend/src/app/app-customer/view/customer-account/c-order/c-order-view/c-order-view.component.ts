import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoginService} from "../../../../../_service/login.service";
import {DatePipe} from "@angular/common";
import {ItemGService} from "../../../../../_service/item-g.service";
import {TimeAgo} from "../../../../../_util/TimeAgo";
import {ItemService} from "../../../../_service/item.service";
import {Router} from "@angular/router";
import {ProfileGService} from "../../../../../_service/profile-g.service";

@Component({
  selector: 'app-c-order-view',
  templateUrl: './c-order-view.component.html',
  styleUrls: ['./c-order-view.component.css']
})
export class COrderViewComponent implements OnInit {

  @Input() shopItemOrders = [];
  @Input() orderIndex;

  // @Output() updateOrders = new EventEmitter();

  constructor(private itemServiceG: ItemGService, private router: Router, private profileService: ProfileGService) {
  }

  ngOnInit(): void {
  }

  // getItemSelected(item) {
  //   // item.orderDetail.quantity = item.quantity;
  //   this.itemServiceG.itemSub.next({
  //     item: item,
  //     backBtn: undefined
  //   });
  // }

  getItemPackageSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined
    });
  }

  timeAgo = new TimeAgo();

  getTimeAgo(time) {
    return this.timeAgo.timeSince(time);
  }

  routeToShop(profileId) {
    this.profileService.profile.profileId = profileId;
    // this.profileService.profile.business = shop;
    this.profileService.profile.returnUrl = '/customer/header/customer_account/customer_order';
    this.profileService.profile.breadCrumb = ['Account', 'Order'];
    this.router.navigate(['/shop/header/shop_view'])
    localStorage.setItem('shop-view', JSON.stringify({id: this.profileService.profile.profileId}))
  }

  // getPreDate() {
  //   let date = new Date();
  //   date.setDate(date.getDate() - 7);
  //   return this.datePipe.transform(date, 'yyyy-MM-dd')
  // }
  //
  // getCurDate() {
  //   return this.datePipe.transform(new Date(), 'yyyy-MM-dd')
  // }

  // updateCustomerOrders(from, to) {
  //   let status;
  //   if (this.orderIndex === 'P') {
  //     status = 'Pending';
  //   } else if (this.orderIndex === 'co') {
  //     status = 'Completed';
  //   } else if (this.orderIndex === 'ca') {
  //     status = 'Canceled';
  //   }
  //   this.updateOrders.emit({
  //     status: status,
  //     from: from.value,
  //     to: to.value
  //   })
  // }
}
