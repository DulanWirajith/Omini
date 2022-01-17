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
    if (this.businessAccountService.businessCategoryId !== undefined) {
      this.getItemOrders(this.businessAccountService.businessCategoryId);
    }
  }

  getItemOrders(businessCategoryId) {
    // this.itemOrders = this.businessAccountService.itemOrders;
    // this.itemService.getItemOrders('B321', businessCategoryId, 'Pending').subscribe((itemOrders) => {
    //   this.itemOrders = itemOrders;
    // })
    this.itemService.getItemOrders(this.loginService.getUser().userId, businessCategoryId, 'Pending').subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
      // this.router.navigate(['/shop/header/business_account/ba_order'])
    })
  }
}
