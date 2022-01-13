import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-ba-order',
  templateUrl: './ba-order.component.html',
  styleUrls: ['./ba-order.component.css']
})
export class BaOrderComponent implements OnInit {

  itemOrders = [];

  constructor(private itemService: ItemService, private businessAccountService: BusinessAccountService) {
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getItemOrders(businessCategoryId);
    })
  }

  ngOnInit(): void {
    if (this.businessAccountService.businessCategoryId !== undefined) {
      this.getItemOrders(this.businessAccountService.businessCategoryId);
    }
  }

  getItemOrders(businessCategoryId) {
    this.itemService.getItemOrders('B321', businessCategoryId, 'Pending').subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
    })
  }
}
