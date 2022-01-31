import {Component, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import * as $ from "jquery";
import {ShopCartService} from "../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../_service/login.service";

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

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    this.itemCategory = itemService.getNewCategory();
    // this.categoryE = itemService.getNewCategory();
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    // this.businessAccountService.businessCategorySub.observers = [];
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.businessCategoryId = businessCategoryId;
      this.getItemCategoriesOrdered(businessCategoryId);
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemCategoriesOrdered();
    this.businessCategories = this.businessAccountService.businessCategories;
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getItemCategoriesOrdered(this.businessAccountService.businessCategory.businessCategoryId);
    }
    this.toggleCategoryBtn();
  }

  onSubmit() {
    this.itemCategory.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };
    // let items = [];
    // for (let item of this.itemCategory.items) {
    //   items.push({
    //     itemId: item.itemPackageId
    //   })
    // }
    // this.itemCategory.items = items;
    console.log(this.itemCategory)
    this.itemService.addCategory(this.itemCategory).subscribe((itemCategory) => {
      // this.items.push(item)
      itemCategory.businessProfileCategory = this.itemCategory.businessProfileCategory;
      this.categories.push(itemCategory)
      // console.log(this.categories)
      this.baManageFormCategory.resetForm(this.itemService.getNewItem());
      if (document.getElementById('btnAddCategory') !== null) {
        document.getElementById('btnAddCategory').click()
      }
      this.itemCategory.isNewCategory = false;
      // this.item.itemItemFeatures = [];
    })
  }

  onSubmitE(itemCategory, index) {
    itemCategory.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };

    this.itemService.updateCategory(itemCategory).subscribe((item) => {
      // this.items.push(item)
      this.baManageFormCategory.resetForm(this.itemService.getNewItem());
      if (document.getElementById('btnCategory' + index) !== null) {
        document.getElementById('btnCategory' + index).click()
      }
      itemCategory.isUpdateCategory = false;
      // this.item.itemItemFeatures = [];
    })
  }

  getItemCategoriesOrdered(businessCategoryId) {
    this.itemService.getItemCategoriesOrdered(this.loginService.getUser().userId, businessCategoryId).subscribe((categories) => {
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
      this.itemService.getItemsBusinessCategory(this.loginService.getUser().userId, this.itemCategory.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
        console.log(items)
        this.itemsToAdd = items;
      })
    } else if (val === 'e') {
      this.itemService.getItemsBusinessCategory(this.loginService.getUser().userId, this.businessCategoryId).subscribe((items) => {
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
    // if (that.categories[index] !== undefined && that.categories[index].items === undefined) {
    that.itemService.getItemCategorySelected($(obj).val()).subscribe((itemCategory) => {
      // console.log(itemCategory)
      // that.categories[index] = category;
      Object.assign(that.categories[index], itemCategory)
      that.categories[index].tempBusinessCategory = itemCategory.businessProfileCategory.businessCategory;
      for (let item of itemCategory.items) {
        // item.itemImgsRaw = [];
        // item.itemItemFeatures = [];
        item.itemPackage.businessProfileCategory = {
          businessProfile: undefined,
          businessCategory: undefined
        }
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
    // }
  }

  confirmation = {
    reply: false,
    message: ''
  };

  categoryIndex;
  itemCategoryE;

  // confirmationSub = new Subject();

  setCategory(itemCategoryE, categoryIndex) {
    this.itemCategoryE = itemCategoryE
    this.categoryIndex = categoryIndex;
    this.setDialogBox('Do you want to remove <b>' + itemCategoryE.name + '</b> ?', false)
  }

  setDialogBox(message, reply = false) {
    this.confirmation.reply = reply;
    this.confirmation.message = message;
  }

  removeCategory() {
    this.itemService.removeCategory(this.itemCategoryE.itemCategoryId).subscribe((reply) => {
      if (reply) {
        this.categories.splice(this.categoryIndex, 1);
        this.setDialogBox('Category has been removed', true);
      }
    }, (error) => {
      this.setDialogBox(error.error, true);
    })
  }

  // setItemPackageAvailable(item) {
  //   this.itemService.setItemPackageAvailable(item.itemId).subscribe((reply) => {
  //     item.itemAvailable = reply;
  //   })
  // }
  //
  // getImageSrc(itemImg) {
  //   let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
  //   return this.sanitizer.bypassSecurityTrustUrl(imageData);
  // }
}
