import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ItemGService} from "../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";

@Component({
  selector: 'app-item-package-detail-view',
  templateUrl: './item-package-detail-view.component.html',
  styleUrls: ['./item-package-detail-view.component.css']
})
export class ItemPackageDetailViewComponent implements OnInit {

  itemPackageObj = {
    itemPackage: undefined,
    backBtn: undefined
  };
  itemPackage;
  _album: [];

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox) {
    this.itemPackage = this.getNewPackage();
    itemService.itemPackageSub.subscribe((itemPackageObj) => {
      // console.log(itemPackageObj)
      this.itemPackageObj = itemPackageObj;
      this.getItemPackageSelected(itemPackageObj.itemPackage);
    })
  }

  ngOnInit(): void {
  }

  getItemPackageSelected(itemPackage) {
    // console.log(item.itemId)
    // console.log(this.items[index])
    // if (this.items[index].itemItemFeatures === undefined) {
    this.itemService.getItemPackageSelected(itemPackage.itemPackageId).subscribe((itemPackage) => {
      // console.log(itemPackage)
      // Object.assign(this.items[index], item)
      // itemPackage.itemImgsRaw = [];
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
      this.itemPackage = itemPackage;
      this.getAlbum(this.itemPackage);
      // this.itemService.itemFeaturesSub.next(item.itemFeatures);
      // this.itemService.itemSub.next(this.items[index]);
      //console.log(this.items[index])
    })
    // } else {
    //   this.itemService.itemSub.next(this.items[index]);
    // }
  }

  getItemSelected(item) {
    this.itemService.itemSub.next({
      item: item,
      backBtn: 'item-package-viewer-g'
    });
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
    imageList.itemPackageImgs.forEach(element => {
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
        src: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemPackageImgName),
        caption: img.itemPackageImgId,
        thumb: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemPackageImgName)
      })
    });
  }

  getAlbumSrc(imgNo) {
    let albumImgSrc;
    albumImgSrc = this._album[imgNo]['thumb'];
    return albumImgSrc;
  }

  getImagePackageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }

  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getNewPackage() {
    return {
      itemPackageId: "",
      name: "",
      description: "",
      price: "",
      discount: "",
      discountType: "None",
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      isNewPackage: false,
      isUpdatePackage: false,
      itemItemPackages: [],
      items: [],
      itemPackageItemPackageFeatures: [],
      itemPackageItemPackageFeature: "",
      itemPkgImgs: [],
      itemPackageImgs: [],
      tempBusinessCategory: undefined,
      tempItems: [],
      tempItemFeatures: []
    }
  }
}
