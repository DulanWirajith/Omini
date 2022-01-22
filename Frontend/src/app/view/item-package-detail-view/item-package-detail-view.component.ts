import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ItemGService} from "../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";

@Component({
  selector: 'app-item-package-detail-view',
  templateUrl: './item-package-detail-view.component.html',
  styleUrls: ['./item-package-detail-view.component.css']
})
export class ItemPackageDetailViewComponent implements OnInit {

  itemPackageObj = {
    itemPackage: undefined,
    backBtn: undefined,
    cart: undefined
  };
  itemPackage;
  _album: [];

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private shopCartService: ShopCartService) {
    this.itemPackage = this.getNewPackage();
    this.itemService.itemPackageSub.observers = [];
    this.itemService.itemPackageSub.subscribe((itemPackageObj) => {
      // console.log(itemPackageObj)
      this.itemPackageObj = itemPackageObj;
      this.getItemPackageSelected(itemPackageObj.itemPackage);
    })
  }

  ngOnInit(): void {
  }

  addToCart() {
    this.itemPackage.orderDetail.orderDetailType = 'ItemPackage';
    this.shopCartService.shopCartSub.next(this.itemPackage);
  }

  getItemPackageSelected(itemPackage) {
    this.itemService.getItemPackageSelected(itemPackage.itemPackageId).subscribe((itemPackageObj) => {
      this.itemPackage = itemPackageObj;
      if (itemPackage.orderDetail !== undefined) {
        this.itemPackage.orderDetail.quantity = itemPackage.orderDetail.quantity
      }
      this.getAlbum(this.itemPackage);
    })
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
