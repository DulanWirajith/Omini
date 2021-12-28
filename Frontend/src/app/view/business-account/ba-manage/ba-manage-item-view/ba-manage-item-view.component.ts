import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-ba-manage-item-view',
  templateUrl: './ba-manage-item-view.component.html',
  styleUrls: ['./ba-manage-item-view.component.css']
})
export class BaManageItemViewComponent implements OnInit {

  @Input() items;
  @Input() itemViewerId;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {

  }

  ngOnInit(): void {
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

  getItemSelected(item) {
    // console.log(item.itemId)
    let index: any = this.items.findIndex(itemObj => {
      return itemObj.itemId === item.itemId
    })
    // console.log(this.items[index])
    if (this.items[index].itemItemFeatures.length === 0) {
      this.itemService.getItemSelected(item.itemId).subscribe((item) => {
        // Object.assign(this.items[index], item)
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
