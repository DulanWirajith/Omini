import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../_service/business-account.service";
import {ItemService} from "../../_service/item.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-business-account',
  templateUrl: './business-account.component.html',
  styleUrls: ['./business-account.component.css']
})
export class BusinessAccountComponent implements OnInit {

  breadCrumbTxt = 'Monitor';
  businessCategories = [];
  businessProfileCategory;
  businessCategory = {
    businessCategoryId: '',
    name: ''
  };
  navOrder: 0;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private router: Router) {
    // businessAccountService.navBarSub.subscribe((val) => {
    //   if (val.name === 'Order') {
    //     this.navOrder = val.value;
    //   }
    //   // this.breadCrumbTxt = txt;
    // })
    this.getBusinessCategories();
  }

  ngOnInit(): void {
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
      this.businessAccountService.businessCategoriesSub.next(businessCategories);
      this.businessAccountService.businessCategories = businessCategories;
      this.businessCategory = {
        businessCategoryId: 'BC002',
        name: 'Cake Baker'
      }
      this.setBusinessCategory();
    })
  }

  setBusinessCategory() {
    // console.log(this.businessCategory)
    this.businessAccountService.businessCategorySub.next(this.businessCategory.businessCategoryId);
    this.businessAccountService.businessCategoryId = this.businessCategory.businessCategoryId;
    this.getItemOrders()
  }

  getItemOrders() {
    this.itemService.getItemOrders('B321', this.businessCategory.businessCategoryId, 'Pending').subscribe((itemOrders) => {
      this.businessAccountService.itemOrders = itemOrders;
      this.navOrder = itemOrders.length;
      this.businessAccountService.navBarSub.next('Order');
      // this.router.navigate(['/shop/header/business_account/ba_order'])
    })
  }

  getItemsOrdered() {
    // this.itemService.getItemsOrdered("B321", this.businessProfileCategory.businessCategoryId, 0, 100).subscribe((items) => {
    //   this.items = items;
    //   for (let item of this.items) {
    //     item.itemImgsRaw = [];
    //     item.businessProfileCategory = {
    //       businessProfile: undefined,
    //       businessCategory: undefined
    //     }
    //   }
    //   //console.log(this.items)
    // })
  }
}
