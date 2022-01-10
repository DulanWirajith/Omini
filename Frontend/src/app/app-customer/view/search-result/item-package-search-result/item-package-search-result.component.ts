import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {environment} from "../../../../../environments/environment";
import * as $ from "jquery";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {ShopCartService} from "../../../_service/shop-cart.service";

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

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer, private shopCartService: ShopCartService) {
    this.shopCartService.shopCartItemsSub.observers = [];
    this.shopCartService.shopCartItemsSub.subscribe((item) => {
      let itemObj: any = this.items.find(itemObj => {
        return itemObj.itemId === item.itemId
      })
      if (itemObj !== undefined) {
        itemObj.itemQty = item.itemQty;
        itemObj.orderDetail.quantity = item.orderDetail.quantity;
      }
    })
    // this.shopCartService.initShopCartSub.observers = [];
    this.shopCartService.initShopCartSub.subscribe((orderDetails) => {
      this.orderDetails = orderDetails;
    })
  }

  ngOnInit(): void {
    this.items = this.itemService.searchedItemPackages.items;
    // console.log(this.items)
    this.itemCount = this.items.length;
    this.itemPackages = this.itemService.searchedItemPackages.itemPackages;
    this.toggleBtns();
    this.setShopCart(this.shopCartService.orderDetails);
  }

  setShopCart(orderDetails) {
    // console.log(orderDetails)
    for (let orderDetail of orderDetails) {
      let itemObj: any = this.items.find(itemObj => {
        return itemObj.itemId === orderDetail.item.itemId
      })
      if (itemObj !== undefined) {
        itemObj.orderDetail.quantity = orderDetail.quantity;
      }
    }
  }

  toggleBtns() {
    let that = this;
    $(document).on('click', '.btnView', function () {
      if (!$(this, '.accordionView').hasClass('show')) {
        that.getItemPackageSelected(that, this);
      }
    })
    $(document).on('click', '#a-item-filters', function () {
      that.itemCount = that.items.length
    })
    $(document).on('click', '#a-package-filters', function () {
      that.itemCount = that.itemPackages.length
    })
  }

  getItemPackageSelected(that, obj) {
    let index: any = that.itemPackages.findIndex(itemPackage => {
      return itemPackage.itemPackageId === $(obj).val()
    })
    //console.log($(obj).val())
    if (that.itemPackages[index] !== undefined && that.itemPackages[index].itemItemPackages === undefined) {
      that.itemService.getItemPackageSelected($(obj).val()).subscribe((itemPackage) => {
        Object.assign(that.itemPackages[index], itemPackage)
        that.itemPackages[index].tempBusinessCategory = itemPackage.businessProfileCategory.businessCategory;
        that.itemPackages[index].items = [];
        // console.log(that.itemPackages[index])
        for (let item of itemPackage.itemItemPackages) {
          // console.log(item.item)
          item.item.businessProfileCategory = that.itemPackages[index].businessProfileCategory;
          that.itemPackages[index].items.push(item.item);
        }
        that.itemPackages[index].tempItems = itemPackage.itemItemPackages;
      })
    }
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

}
