import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {environment} from "../../../../../environments/environment";
import * as $ from "jquery";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {ShopCartService} from "../../../_service/shop-cart.service";
import {ItemGService} from "../../../../_service/item-g.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-item-package-search-result',
  templateUrl: './item-package-search-result.component.html',
  styleUrls: ['./item-package-search-result.component.css']
})
export class ItemPackageSearchResultComponent implements OnInit {

  items = [];
  itemPackages = [];
  itemCount = 0;
  orderDetails = [];

  constructor(private itemService: ItemService, private itemServiceG: ItemGService, private sanitizer: DomSanitizer, private shopCartService: ShopCartService,
              private notifierService: NotifierService) {
    // this.shopCartService.shopCartItemsSub.observers = [];
    this.shopCartService.shopCartItemsSub.subscribe((item) => {
      // console.log(item)
      // console.log(this.items)
      // let itemObj: any = this.items.find(itemObj => {
      //   return itemObj.itemId === item.itemId
      // })
      // if (itemObj !== undefined) {
      //   // console.log(itemObj)
      //   itemObj.quantity = item.quantity;
      //   itemObj.orderDetail.quantity = item.orderDetail.quantity;
      // }
      let itemPackageObj: any = this.itemPackages.find(itemPackageObj => {
        return itemPackageObj.itemPackageId === item.itemPackageId
      })
      if (itemPackageObj !== undefined) {
        // itemObj.quantity = item.quantity;
        itemPackageObj.orderDetail.quantity = item.orderDetail.quantity;
      }
    })
    this.shopCartService.initShopCartSub.observers = [];
    this.shopCartService.initShopCartSub.subscribe((orderDetails) => {
      this.orderDetails = orderDetails;
    })
  }

  ngOnInit(): void {
    console.log(this.itemService.searchedItemPackages)
    this.items = this.itemService.searchedItemPackages.items;
    // console.log(this.items)
    this.itemCount = this.itemService.searchedItemPackages.items.length;
    this.itemPackages = this.itemService.searchedItemPackages.itemPackages;
    this.toggleBtns();
    this.setShopCart(this.shopCartService.shopCart);
  }

  // addToCart(itemPackage) {
  //   // console.log(itemPackage)
  //   //console.log(this.shopCartService.shopCartSub)
  //   itemPackage.orderDetail.orderDetailType = 'ItemPackage';
  //   this.shopCartService.shopCartSub.next(itemPackage);
  // }

  addToCart(item, orderDetailType) {
    // console.log(item)
    //console.log(this.shopCartService.shopCartSub)
    item.orderDetail.orderDetailType = orderDetailType;
    this.shopCartService.shopCartSub.next(item);
  }

  // calcDiscount(itemPackage) {
  //   if (itemPackage.discountType === 'Cash') {
  //     return itemPackage.price - itemPackage.discount;
  //   } else if (itemPackage.discountType === 'Percentage') {
  //     return itemPackage.price * ((100 - itemPackage.discount) / 100);
  //   }
  //   return '';
  // }

  setShopCart(shopCart) {
    // console.log(shopCart)
    for (let shop of shopCart) {
      for (let item of shop.items) {
        // console.log(orderDetail)
        let orderDetail = item.orderDetail;
        if (orderDetail.orderDetailType === 'Item') {
          let itemObj: any = this.items.find(itemObj => {
            return itemObj.itemId === orderDetail.item.itemId
          })
          if (itemObj !== undefined) {
            itemObj.orderDetail.quantity = orderDetail.quantity;
          }
        } else if (orderDetail.orderDetailType === 'ItemPackage') {
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

  getPackageItemSelected(itemPackage) {
    // console.log(itemPackage)
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined,
      cart: true
    });
    // let index: any = that.packageItems.findIndex(itemPackage => {
    //   return itemPackage.itemPackageId === $(obj).val()
    // })
    // //console.log($(obj).val())
    // if (that.packageItems[index] !== undefined && that.packageItems[index].itemItemPackages === undefined) {
    //   that.itemService.getItemPackageSelected($(obj).val()).subscribe((itemPackage) => {
    //     Object.assign(that.packageItems[index], itemPackage)
    //     that.packageItems[index].tempBusinessCategory = itemPackage.businessProfileCategory.businessCategory;
    //     that.packageItems[index].items = [];
    //     // console.log(that.packageItems[index])
    //     for (let item of itemPackage.itemItemPackages) {
    //       // console.log(item.item)
    //       item.item.businessProfileCategory = that.packageItems[index].businessProfileCategory;
    //       that.packageItems[index].items.push(item.item);
    //     }
    //     that.packageItems[index].tempItems = itemPackage.itemItemPackages;
    //   })
    // }
  }

  getItemSelected(item) {
    this.itemServiceG.itemSub.next({
      item: item,
      backBtn: undefined,
      cart: true
    });
    // console.log(item.itemId)
    // let index: any = this.items.findIndex(itemObj => {
    //   return itemObj.itemId === item.itemId
    // })
    // // console.log(this.items[index])
    // if (this.items[index].itemItemFeatures === undefined) {
    //   this.itemService.getItemSelected(item.itemId).subscribe((item) => {
    //     // Object.assign(this.items[index], item)
    //     // item.itemImgsRaw = [];
    //     // item.itemItemFeatures = [];
    //     // item.businessProfileCategory = {
    //     //   businessProfile: undefined,
    //     //   businessCategory: undefined
    //     // }
    //     // if (item.itemDiscountType === "None") {
    //     //   item.itemDiscountView = "N/A";
    //     // } else if (item.itemDiscountType === "Cash") {
    //     //   item.itemDiscountView = "LKR " + item.itemDiscount;
    //     // } else if (item.itemDiscountType === "Percentage") {
    //     //   item.itemDiscountView = item.itemDiscount + "%";
    //     // }
    //     item.itemCount = this.items[index].itemCount;
    //     this.items[index] = item;
    //     // this.itemService.itemFeaturesSub.next(item.itemFeatures);
    //     this.itemService.itemSub.next(this.items[index]);
    //     //console.log(this.items[index])
    //   })
    // } else {
    //   this.itemService.itemSub.next(this.items[index]);
    // }
  }

  setItemFavourite(item) {
    this.itemService.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, item.itemId).subscribe((reply) => {
      item.favourite = reply;
    })
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.imageName);
  }

  // t(){
  //   console.log(5)
  //   this.notifierService.notify("success", "Item deleted successfully");
  // }
}
