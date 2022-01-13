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
  itemsToAddE = [];
  businessCategories = [];
  itemCategory;
  categories = [];
  businessProfileCategory;
  businessCategoryId;
  @ViewChild('baManageFormCategory', {static: true}) public baManageFormCategory: NgForm;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.itemCategory = itemService.getNewCategory();
    // this.categoryE = itemService.getNewCategory();
    businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
      // console.log(this.businessCategories)
    })
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.businessCategoryId = businessCategoryId;
      this.getItemCategoriesOrdered(businessCategoryId);
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemCategoriesOrdered();
    this.businessCategories = this.businessAccountService.businessCategories;
    if (this.businessAccountService.businessCategoryId !== undefined) {
      this.getItemCategoriesOrdered(this.businessAccountService.businessCategoryId);
    }
    this.toggleCategoryBtn();
  }

  onSubmit() {
    this.itemCategory.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    this.itemService.addCategory(this.itemCategory).subscribe((item) => {
      // this.items.push(item)
      this.baManageFormCategory.resetForm(this.itemService.getNewItem());
      // this.item.itemItemFeatures = [];
    })
  }

  onSubmitE(itemCategory) {
    itemCategory.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    this.itemService.updateCategory(itemCategory).subscribe((item) => {
      // this.items.push(item)
      this.baManageFormCategory.resetForm(this.itemService.getNewItem());
      // this.item.itemItemFeatures = [];
    })
  }

  getItemCategoriesOrdered(businessCategoryId) {
    this.itemService.getItemCategoriesOrdered("B321", businessCategoryId).subscribe((categories) => {
      // console.log(categories)
      this.categories = categories;
      for (let itemCategory of this.categories) {
        itemCategory.businessProfileCategory = {
          businessProfile: undefined,
          businessCategory: undefined
        }
        itemCategory.isUpdateCategory = false;
      }
    })
    this.getItems('e');
  }

  // getBusinessCategories() {
  //   this.businessCategories = this.businessAccountService.businessCategories;
  // }

  getItems(val, itemCategory?) {
    if (val === 'n') {
      this.itemService.getItemsBusinessCategory("B321", this.itemCategory.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
        // console.log(items)
        this.itemsToAdd = items;
      })
    } else if (val === 'e') {
      this.itemService.getItemsBusinessCategory("B321", this.businessCategoryId).subscribe((items) => {
        // console.log(items)
        this.itemsToAddE = items;
        if (itemCategory !== undefined) {
          // console.log(itemCategory)
          if (itemCategory.businessProfileCategory.businessCategory.businessCategoryId === itemCategory.tempBusinessCategory.businessCategoryId) {
            itemCategory.items = itemCategory.tempItems;
          } else {
            itemCategory.items = [];
          }
        }
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
    let index: any = that.categories.findIndex(itemCategory => {
      return itemCategory.itemCategoryId === $(obj).val()
    })
    // console.log(categoryObj)that.categories[index]
    if (that.categories[index] !== undefined && that.categories[index].items === undefined) {
      that.itemService.getItemCategorySelected($(obj).val()).subscribe((itemCategory) => {
        // that.categories[index] = category;
        Object.assign(that.categories[index], itemCategory)
        that.categories[index].tempBusinessCategory = itemCategory.businessProfileCategory.businessCategory;
        for (let item of itemCategory.items) {
          // item.itemImgsRaw = [];
          // item.itemItemFeatures = [];
          // item.businessProfileCategory = {
          //   businessProfile: undefined,
          //   businessCategory: undefined
          // }
          // this.items.push(item.item);
        }
        that.categories[index].tempItems = itemCategory.items;
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

  // setItemAvailable(item) {
  //   this.itemService.setItemAvailable(item.itemId).subscribe((reply) => {
  //     item.itemAvailable = reply;
  //   })
  // }
  //
  // getImageSrc(itemImg) {
  //   let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
  //   return this.sanitizer.bypassSecurityTrustUrl(imageData);
  // }
}
