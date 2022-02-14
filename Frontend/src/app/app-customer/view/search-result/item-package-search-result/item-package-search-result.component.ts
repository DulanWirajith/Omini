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

  items = [];
  itemPackages = [];
  itemCount = 0;
  orderDetails = [];

  constructor(private itemService: ItemService, private itemServiceG: ItemGService, private sanitizer: DomSanitizer, private shopCartService: ShopCartService,
              private notifierService: NotifierService, private router: Router, private profileService: ProfileGService, private loginService: LoginService) {
    // if (ItemPackageSearchResultComponent.lastComp === undefined) {
    this.shopCartService.shopCartItemsSub.subscribe((item) => {
      let itemObj: any = this.items.find(itemObj => {
        return itemObj.itemPackageId === item.itemPackageId
      })
      if (itemObj !== undefined) {
        itemObj.quantity = item.quantity;
        itemObj.orderDetail.quantity = item.orderDetail.quantity;
      }
      let itemPackageObj: any = this.itemPackages.find(itemPackageObj => {
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
      this.itemService.searchedItemPackages = searchedItemPackages;
      this.items = searchedItemPackages['items'];
      this.itemCount = searchedItemPackages['items'].length;
      this.itemPackages = searchedItemPackages['itemPackages'];
      this.setShopCart(this.shopCartService.shopCart);
    })
  }

  ngOnInit(): void {
    this.items = this.itemService.searchedItemPackages.items;
    this.itemCount = this.itemService.searchedItemPackages.items.length;
    this.itemPackages = this.itemService.searchedItemPackages.itemPackages;
    this.toggleBtns();
    this.setShopCart(this.shopCartService.shopCart);
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
          let itemObj: any = this.items.find(itemObj => {
            return itemObj.itemPackageId === orderDetail.itemPackage.itemPackageId
          })
          if (itemObj !== undefined) {
            itemObj.orderDetail.quantity = orderDetail.quantity;
          }
        } else if (orderDetail.orderDetailType === 'Package') {
          let itemPackageObj: any = this.itemPackages.find(itemPackageObj => {
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
      that.itemCount = that.itemPackages.length
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
    this.router.navigate(['/shop/header/shop_view'])
    localStorage.setItem('shop-view', JSON.stringify({id: this.profileService.profile.profileId}))
  }

  setItemFavourite(itemPackage) {
    this.itemService.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, itemPackage.itemPackageId).subscribe((reply) => {
      itemPackage.favourite = reply;
    })
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
