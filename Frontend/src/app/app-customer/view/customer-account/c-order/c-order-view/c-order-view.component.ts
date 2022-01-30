import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoginService} from "../../../../../_service/login.service";
import {DatePipe} from "@angular/common";
import {ItemGService} from "../../../../../_service/item-g.service";
import {TimeAgo} from "../../../../../_util/TimeAgo";

@Component({
  selector: 'app-c-order-view',
  templateUrl: './c-order-view.component.html',
  styleUrls: ['./c-order-view.component.css']
})
export class COrderViewComponent implements OnInit {

  @Input() shopItemOrders = [];
  @Input() orderIndex;

  // @Output() updateOrders = new EventEmitter();

  constructor(private itemServiceG: ItemGService) {
  }

  ngOnInit(): void {
  }

  getItemPackageSelected(item) {
    // item.orderDetail.quantity = item.quantity;
    this.itemServiceG.itemSub.next({
      item: item,
      backBtn: undefined
    });
  }

  getPackageItemSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined
    });
  }

  timeAgo = new TimeAgo();

  getTimeAgo(time) {
    return this.timeAgo.timeSince(time);
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
