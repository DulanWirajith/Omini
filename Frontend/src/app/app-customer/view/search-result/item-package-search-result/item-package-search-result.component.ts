import {Component, OnDestroy, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from "../../../../../environments/environment";
import * as $ from "jquery";
import {ShopCartService} from "../../../_service/shop-cart.service";
import {ItemGService} from "../../../../_service/item-g.service";
import {NotifierService} from "angular-notifier";
import {LoginService} from "../../../../_service/login.service";
import {Router} from "@angular/router";
import {ProfileGService} from "../../../../_service/profile-g.service";

@Component({
  selector: 'app-item-package-search-result',
  templateUrl: './item-package-search-result.component.html',
  styleUrls: ['./item-package-search-result.component.css']
})
export class ItemPackageSearchResultComponent implements OnInit {

  // static lastComp: ItemPackageSearchResultComponent;
  tempItems = [];
  tempPackageItems = [];
  items = [];
  packageItems = [];
  itemCount = 0;
  orderDetails = [];

  constructor(private itemService: ItemService, private itemServiceG: ItemGService, private sanitizer: DomSanitizer, private shopCartService: ShopCartService,
              private notifierService: NotifierService, private router: Router, private profileService: ProfileGService, private loginService: LoginService) {
    // if (ItemPackageSearchResultComponent.lastComp === undefined) {
    this.shopCartService.shopCartItemsSub.subscribe((item) => {
      let itemObj: any = this.tempItems.find(itemObj => {
        return itemObj.itemPackageId === item.itemPackageId
      })
      if (itemObj !== undefined) {
        itemObj.quantity = item.quantity;
        itemObj.orderDetail.quantity = item.orderDetail.quantity;
      }
      let itemPackageObj: any = this.tempPackageItems.find(itemPackageObj => {
        return itemPackageObj.itemPackageId === item.itemPackageId
      })
      if (itemPackageObj !== undefined) {
        itemPackageObj.quantity = item.quantity;
        itemPackageObj.orderDetail.quantity = item.orderDetail.quantity;
      }
    })
    this.shopCartService.initShopCartSub.observers = [];
    this.shopCartService.initShopCartSub.subscribe((orderDetails) => {
      this.orderDetails = orderDetails;
    })
    this.itemService.searchedItemPackagesSub.subscribe((searchedItemPackages) => {
      // console.log(searchedItemPackages)
      this.itemCount = 0;
      this.itemService.searchedItemPackages = searchedItemPackages;
      this.tempItems = searchedItemPackages['items'];
      // this.itemCount = searchedItemPackages['items'].length;
      this.tempPackageItems = searchedItemPackages['itemPackages'];
      this.initItems();
    })
  }

  ngOnInit(): void {
    this.tempItems = this.itemService.searchedItemPackages.items;
    // this.itemCount = this.itemService.searchedItemPackages.items.length;
    this.tempPackageItems = this.itemService.searchedItemPackages.itemPackages;
    this.toggleBtns();
    this.initItems();
    // this.setShopCart(this.shopCartService.shopCart);
    // this.categories = {items: [], packageItems: []};
    // this.items = [];
    // this.packageItems = [];
    // this.setCategories(this.tempItems, 'Item');
    // this.setCategories(this.tempPackageItems, 'PackageItem');
    // this.categorizeItems(this.categories.items[0], 'Item');
    // this.categorizeItems(this.categories.packageItems[0], 'PackageItem');
    // this.setCategories(this.packageItems)
    // this.categorizeItems(this.items, 'Item')
    // this.categorizeItems(this.packageItems, 'PackageItem')
  }

  initItems() {
    this.setShopCart(this.shopCartService.shopCart);
    this.categories = {items: [], packageItems: []};
    this.items = [];
    this.packageItems = [];
    this.setCategories(this.tempItems, 'Item');
    this.setCategories(this.tempPackageItems, 'PackageItem');
    this.categorizeItems(this.categories.items[0], 'Item');
    this.categorizeItems(this.categories.packageItems[0], 'PackageItem');
  }

  // ngOnDestroy(): void {
  //   ItemPackageSearchResultComponent.lastComp = this;
  // }

  addToCart(item, orderDetailType) {
    item.orderDetail.orderDetailType = orderDetailType;
    this.shopCartService.shopCartSub.next(item);
  }

  setShopCart(shopCart) {
    for (let shop of shopCart) {
      for (let item of shop.items) {
        let orderDetail = item.orderDetail;
        // console.log(orderDetail)
        if (orderDetail.orderDetailType === 'Item') {
          let itemObj: any = this.tempItems.find(itemObj => {
            return itemObj.itemPackageId === orderDetail.itemPackage.itemPackageId
          })
          if (itemObj !== undefined) {
            itemObj.orderDetail.quantity = orderDetail.quantity;
          }
        } else if (orderDetail.orderDetailType === 'Package') {
          let itemPackageObj: any = this.tempPackageItems.find(itemPackageObj => {
            return itemPackageObj.itemPackageId === orderDetail.itemPackage.itemPackageId
          })
          if (itemPackageObj !== undefined) {
            // itemObj.quantity = item.quantity;
            itemPackageObj.orderDetail.quantity = orderDetail.quantity;
          }
        }
      }
    }
  }

  toggleBtns() {
    let that = this;
    // $(document).on('click', '.btnView', function () {
    //   if (!$(this, '.accordionView').hasClass('show')) {
    //     that.getItemPackageSelected(that, this);
    //   }
    // })
    $(document).on('click', '#a-item-filters', function () {
      that.itemCount = that.items.length
    })
    $(document).on('click', '#a-package-filters', function () {
      that.itemCount = that.packageItems.length
    })
  }

  getItemPackageSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined,
      cart: true
    });
  }

  routeToShop(profileId) {
    this.profileService.profile.profileId = profileId;
    this.profileService.profile.returnUrl = '/customer/header/search_result/item_package_search_result';
    this.profileService.profile.breadCrumb = ['Search Item'];
    this.router.navigate(['/shop/header/shop_view']);
    localStorage.setItem('shop-view', JSON.stringify({id: this.profileService.profile.profileId}))
  }

  setItemFavourite(itemPackage) {
    this.itemService.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, itemPackage.itemPackageId).subscribe((reply) => {
      itemPackage.favourite = reply;
    })
  }

  categories = {items: [], packageItems: []};

  setCategories(items, type) {
    for (let item of items) {
      if (type === 'Item') {
        let category = this.categories.items.findIndex((businessCategory) => {
          return businessCategory.category !== undefined && businessCategory.category.businessCategoryId === item.businessProfileCategory.businessCategory.businessCategoryId
        });
        if (category === -1) {
          this.categories.items.push({
            category: item.businessProfileCategory.businessCategory,
            items: []
          })
        }
      } else if (type === 'PackageItem') {
        let category = this.categories.packageItems.findIndex((businessCategory) => {
          return businessCategory.category !== undefined && businessCategory.category.businessCategoryId === item.businessProfileCategory.businessCategory.businessCategoryId
        });
        if (category === -1) {
          this.categories.packageItems.push({
            category: item.businessProfileCategory.businessCategory,
            packageItems: []
          })
        }
      }
    }
    this.selectedItemCategory = this.categories.items[0];
    this.selectedPackageItemCategory = this.categories.packageItems[0];
  }

  selectedItemCategory;
  selectedPackageItemCategory;

  categorizeItems(category, type) {
    if (category !== undefined) {
      if (type === 'Item') {
        this.selectedItemCategory = category.category.businessCategoryId;
        this.items = this.tempItems.filter((packageItem) => {
          return category.category.businessCategoryId === packageItem.businessProfileCategory.businessCategory.businessCategoryId
        })
        this.itemCount = this.items.length
      } else if (type === 'PackageItem') {
        this.selectedPackageItemCategory = category.category.businessCategoryId;
        this.packageItems = this.tempPackageItems.filter((packageItem) => {
          return category.category.businessCategoryId === packageItem.businessProfileCategory.businessCategory.businessCategoryId
        })
        // this.itemCount = this.packageItems.length
      }
    }
    // console.log(this.categories)
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}
