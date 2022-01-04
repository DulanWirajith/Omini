import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {environment} from "../../../../../environments/environment";
import * as $ from "jquery";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-item-package-search-result',
  templateUrl: './item-package-search-result.component.html',
  styleUrls: ['./item-package-search-result.component.css']
})
export class ItemPackageSearchResultComponent implements OnInit {

  items = [];
  itemPackages = [];
  itemCount = 0;

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer, private config: NgbCarouselConfig) {
    config.showNavigationArrows = true;
    config.showNavigationIndicators = true;
  }

  ngOnInit(): void {
    this.items = this.itemService.searchedItemPackages.items;
    this.itemCount = this.items.length;
    this.itemPackages = this.itemService.searchedItemPackages.itemPackages;
    this.toggleBtns();
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
        // that.categories[index] = category;
        Object.assign(that.itemPackages[index], itemPackage)
        that.itemPackages[index].tempBusinessCategory = itemPackage.businessProfileCategory.businessCategory;
        that.itemPackages[index].items = [];
        for (let item of itemPackage.itemItemPackages) {
          that.itemPackages[index].items.push(item.item);
        }
        that.itemPackages[index].tempItems = itemPackage.itemItemPackages;
        // console.log(that.itemPackages[index])
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

  getItemSelected(item) {
    // console.log(item.itemId)
    let index: any = this.items.findIndex(itemObj => {
      return itemObj.itemId === item.itemId
    })
    // console.log(this.items[index])
    if (this.items[index].itemItemFeatures === undefined) {
      this.itemService.getItemSelected(item.itemId).subscribe((item) => {
        // Object.assign(this.items[index], item)
        // item.itemImgsRaw = [];
        // item.itemItemFeatures = [];
        // item.businessProfileCategory = {
        //   businessProfile: undefined,
        //   businessCategory: undefined
        // }
        // if (item.itemDiscountType === "None") {
        //   item.itemDiscountView = "N/A";
        // } else if (item.itemDiscountType === "Cash") {
        //   item.itemDiscountView = "LKR " + item.itemDiscount;
        // } else if (item.itemDiscountType === "Percentage") {
        //   item.itemDiscountView = item.itemDiscount + "%";
        // }
        this.items[index] = item;
        // this.itemService.itemFeaturesSub.next(item.itemFeatures);
        this.itemService.itemSub.next(this.items[index]);
        //console.log(this.items[index])
      })
    } else {
      this.itemService.itemSub.next(this.items[index]);
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
