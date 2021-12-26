import {Component, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import * as $ from "jquery";

@Component({
  selector: 'app-ba-manage-category',
  templateUrl: './ba-manage-category.component.html',
  styleUrls: ['./ba-manage-category.component.css']
})
export class BaManageCategoryComponent implements OnInit {

  itemsToAdd = [];
  addedItems;
  addedItemsE;
  businessCategories = [];
  category;
  // categoryE;
  categories = [];
  businessProfileCategory;
  @ViewChild('baManageFormCategory', {static: true}) public baManageFormCategory: NgForm;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.category = itemService.getNewCategory();
    // this.categoryE = itemService.getNewCategory();
    businessAccountService.businessCategories.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
      // console.log(this.businessCategories)
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemCategoriesOrdered();
    this.toggleCategoryBtn();
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

  onSubmitE() {

  }

  getItemCategoriesOrdered() {
    this.itemService.getItemCategoriesOrdered("B321", this.businessProfileCategory.businessCategoryId).subscribe((categories) => {
      // console.log(categories)
      this.categories = categories;
      for (let category of this.categories) {
        category.businessProfileCategory = {
          businessProfile: undefined,
          businessCategory: undefined
        }
      }
    })
  }

  // getBusinessCategories() {
  //   this.businessCategories = this.businessAccountService.businessCategories;
  // }

  getItems(val) {
    if (val === 'n') {
      this.itemService.getItemsBusinessCategory("B321", this.category.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
        // console.log(items)
        this.itemsToAdd = items;
      })
    }
  }

  toggleCategoryBtn() {
    let that = this;
    $(document).on('click', '.accordion-button', function () {
      if (!$(this).hasClass('collapsed')) {
        that.getItemCategorySelected(that, this);
      }
    })
    $(document).on('click', '.hover-text-info', function () {
      if (!$(this).hasClass('collapsed')) {
        that.getItemCategorySelected(that, this);
      }
    })
  }

  getItemCategorySelected(that, obj) {
    let index: any = that.categories.findIndex(category => {
      return category.itemCategoryId === $(obj).val()
    })
    // console.log(categoryObj)that.categories[index]
    if (that.categories[index] !== undefined && that.categories[index].items === undefined) {
      that.itemService.getItemCategorySelected($(obj).val()).subscribe((category) => {
        // that.categories[index] = category;
        Object.assign(that.categories[index], category)
        // console.log(that.categories[index])
        // for (let i = 0; i < that.categories.length; i++) {
        //   if (that.categories[i].itemCategoryId === $(obj).val()) {
        //     // console.log(category)
        //     that.categoryE = category;
        //     that.categories[i].items = category.items;
        //   }
        // }
      })
    }
  }

  setItemAvailable(item) {
    this.itemService.setItemAvailable(item.itemId).subscribe((reply) => {
      item.itemAvailable = reply;
    })
  }

  getImageSrc(itemImg) {
    let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    return this.sanitizer.bypassSecurityTrustUrl(imageData);
  }
}
