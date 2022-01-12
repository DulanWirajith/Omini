import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../_service/business-account.service";

@Component({
  selector: 'app-business-account',
  templateUrl: './business-account.component.html',
  styleUrls: ['./business-account.component.css']
})
export class BusinessAccountComponent implements OnInit {

  breadCrumbTxt = 'Monitor';
  businessCategories = [];
  businessProfileCategory;
  businessCategory;

  constructor(private businessAccountService: BusinessAccountService) {
    // businessAccountService.breadCrumbSub.subscribe((txt) => {
    //   this.breadCrumbTxt = txt;
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
    })
  }

  setBusinessCategory() {
    // console.log(this.businessCategory)
    this.businessAccountService.businessCategorySub.next(this.businessCategory.businessCategoryId);
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
