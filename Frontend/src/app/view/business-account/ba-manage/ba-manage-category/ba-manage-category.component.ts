import {Component, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-ba-manage-category',
  templateUrl: './ba-manage-category.component.html',
  styleUrls: ['./ba-manage-category.component.css']
})
export class BaManageCategoryComponent implements OnInit {

  itemsToAdd = [];
  addedItems;
  businessCategories;
  category;
  @ViewChild('baManageFormCategory', {static: true}) public baManageFormCategory: NgForm;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.category = itemService.getNewCategory();
    businessAccountService.businessCategories.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
  }

  onSubmit() {
    this.category.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    this.itemService.addCategory(this.category).subscribe((item) => {
      // this.items.push(item)
      this.baManageFormCategory.resetForm(this.itemService.getNewItem());
      // this.item.itemItemFeatures = [];
    })
  }

  // getBusinessCategories() {
  //   this.businessCategories = this.businessAccountService.businessCategories;
  // }

  getItems(val) {
    if (val === 'n') {
      this.itemService.getItemsBusinessCategory("B321", this.category.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
        console.log(items)
        this.itemsToAdd = items;
      })
    }
  }
}
