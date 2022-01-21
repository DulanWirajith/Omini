import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ItemGService} from "../../_service/item-g.service";

@Component({
  selector: 'app-item-detail-view',
  templateUrl: './item-detail-view.component.html',
  styleUrls: ['./item-detail-view.component.css']
})
export class ItemDetailViewComponent implements OnInit {

  itemObj = {
    item: undefined,
    backBtn: undefined
  };
  item;
  _album: [];

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox) {
    this.item = this.getNewItem();
    itemService.itemSub.subscribe((itemObj) => {
      this.itemObj = itemObj;
      this.getItemSelected(itemObj.item);
    })
  }

  ngOnInit(): void {
  }

  getItemSelected(item) {
    // console.log(item.itemId)
    // console.log(this.items[index])
    // if (this.items[index].itemItemFeatures === undefined) {
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
      this.item = item;
      this.getAlbum(this.item);
      // this.itemService.itemFeaturesSub.next(item.itemFeatures);
      // this.itemService.itemSub.next(this.items[index]);
      //console.log(this.items[index])
    })
    // } else {
    //   this.itemService.itemSub.next(this.items[index]);
    // }
  }

  open(index: number): void {
    // open lightbox
    this.lightbox.open(this._album, index);
  }

  close(): void {
    // close lightbox programmatically
    this.lightbox.close();
  }

  getAlbum(imageList) {
    let promises = [];
    imageList.itemImgs.forEach(element => {
      promises.push(this.setAlbum(element));
    });
    Promise.all(promises).then((result: []) => {
      this._album = result;
      //console.log(this._album);
    });
  }

  setAlbum(img) {
    return new Promise((resolve, reject) => {
      resolve({
        src: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemImgName),
        caption: img.itemImgId,
        thumb: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemImgName)
      })
    });
  }

  getAlbumSrc(imgNo) {
    let albumImgSrc;
    albumImgSrc = this._album[imgNo]['thumb'];
    return albumImgSrc;
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }

  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getNewItem() {
    return {
      itemId: "",
      name: "",
      quantity: 1,
      itemCount: 0,
      discount: "",
      discountType: "None",
      price: "",
      description: "",
      itemImgs: [],
      itemImgsRaw: [],
      available: false,
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      itemItemFeatures: [],
      isNewItem: false,
      isUpdateItem: false,
    }
  }
}
