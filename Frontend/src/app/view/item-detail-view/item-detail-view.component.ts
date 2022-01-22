import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ItemGService} from "../../_service/item-g.service";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";

@Component({
  selector: 'app-item-detail-view',
  templateUrl: './item-detail-view.component.html',
  styleUrls: ['./item-detail-view.component.css']
})
export class ItemDetailViewComponent implements OnInit {

  itemObj = {
    item: undefined,
    backBtn: undefined,
    cart: undefined
  };
  item;
  _album: [];

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private shopCartService: ShopCartService) {
    this.item = this.getNewItem();
    this.itemService.itemSub.observers = [];
    this.itemService.itemSub.subscribe((itemObj) => {
      this.itemObj = itemObj;
      this.getItemSelected(itemObj.item);
      // console.log(itemObj)
    })
  }

  ngOnInit(): void {
  }

  getItemSelected(item) {
    this.itemService.getItemSelected(item.itemId).subscribe((itemObj) => {
      this.item = itemObj;
      if (item.orderDetail !== undefined) {
        this.item.orderDetail.quantity = item.orderDetail.quantity
      }
      this.getAlbum(this.item);
    })
  }

  addToCart() {
    this.item.orderDetail.orderDetailType = 'Item';
    this.shopCartService.shopCartSub.next(this.item);
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
