import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-ba-manage-item-view',
  templateUrl: './ba-manage-item-view.component.html',
  styleUrls: ['./ba-manage-item-view.component.css'],
  providers: [NgbCarouselConfig]
})
export class BaManageItemViewComponent implements OnInit {

  @Input() items;
  @Input() itemViewerId;
  showNavigationArrows = true;
  showNavigationIndicators = false;
  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer,private config: NgbCarouselConfig) {
    config.showNavigationArrows = true;
    config.showNavigationIndicators = true;
  }

  ngOnInit(): void {
  }


  setItemAvailable(item) {
    this.itemService.setItemAvailable(item.itemId).subscribe((reply) => {
      item.itemAvailable = reply;
    })
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
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
        item.itemImgsRaw = [];
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
        this.itemService.itemFeaturesSub.next(item.itemFeatures);
        this.itemService.itemSub.next(this.items[index]);
        console.log(this.items[index])
      })
    } else {
      this.itemService.itemSub.next(this.items[index]);
    }
  }

}
