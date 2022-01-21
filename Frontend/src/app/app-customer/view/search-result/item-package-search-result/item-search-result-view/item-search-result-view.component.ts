import {Component, Input, OnInit} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {environment} from "../../../../../../environments/environment";
import {ShopCartService} from "../../../../_service/shop-cart.service";
import {ItemGService} from "../../../../../_service/item-g.service";


@Component({
  selector: 'app-item-search-result-view',
  templateUrl: './item-search-result-view.component.html',
  styleUrls: ['./item-search-result-view.component.css']
})
export class ItemSearchResultViewComponent implements OnInit {

  @Input() items = [];

  // shopCartItems = [];

  constructor(private itemService: ItemService, private itemServiceG: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private shopCartService: ShopCartService) {
  }

  ngOnInit(): void {

  }

  addToCart(item) {
    // console.log(item)
    //console.log(this.shopCartService.shopCartSub)
    item.orderDetail.orderDetailType = 'Item';
    this.shopCartService.shopCartSub.next(item);
  }

  // calcDiscount(item) {
  //   // console.log(item)
  //   if (item.discountType === 'Cash') {
  //     return item.price - item.discount;
  //   } else if (item.discountType === 'Percentage') {
  //     return item.price * ((100 - item.discount) / 100);
  //   }
  //   return '';
  // }

  getItemSelected(item) {
    this.itemServiceG.itemSub.next({
      item: item,
      backBtn: undefined,
      cart: item.orderDetail.quantity
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

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }

}
