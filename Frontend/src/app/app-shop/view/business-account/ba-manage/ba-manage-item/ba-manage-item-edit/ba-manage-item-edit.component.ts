import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {Lightbox} from 'ngx-lightbox';
import {ShopCartService} from "../../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../../_service/login.service";

@Component({
  selector: 'app-ba-manage-item-edit',
  templateUrl: './ba-manage-item-edit.component.html',
  styleUrls: ['./ba-manage-item-edit.component.css']
})
export class BaManageItemEditComponent implements OnInit {

  itemIndex;
  items;
  item;
  itemFeatures;
  @ViewChild('imageInput') imageInput: any;
  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }
  businessCategories = [];
  itemFeature;
  isNewFeature;
  newItemFeature;
  newItemFeaturesTemp = [];
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  _album: [];

  // lightbox;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private loginService: LoginService) {
    this.item = this.itemService.getNewItem();
    // this.lightbox = _lightbox;
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    this.itemService.itemSub.observers = [];
    this.itemService.itemSub.subscribe((item) => {
      this.item = item.item;
      this.items = item.items;
      this.itemIndex = item.index
      this.getAlbum(this.item);
    })
    this.itemService.itemFeaturesSub.observers = [];
    this.itemService.itemFeaturesSub.subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
    })
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
    let albumImgSrc = '';
    if (this._album[imgNo] !== undefined) {
      albumImgSrc = this._album[imgNo]['thumb'];
    }
    return albumImgSrc;
  }

  ngOnInit(): void {
    this.businessCategories = this.businessAccountService.businessCategories;
  }

  onSubmitE() {
    this.item.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };
    if (this.item.discount === 'N/A') {
      this.item.discount = 0;
    }
    // for (let item of this.item.itemItemFeatures) {
    //   item.name = item.item.name;
    // }
    //console.log(this.item)
    const uploadImageData = new FormData();
    for (let itemImg of this.item.itemImgsRaw) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.itemService.updateItem(uploadImageData, this.item.itemId).subscribe((item) => {
      this.imageInput.removeFiles();
      this.item.itemImgs = this.item.itemImgs.concat(item.itemImgs);
      if (document.getElementById('item-back-btn') !== null) {
        document.getElementById('item-back-btn').click();
      }
    })
  }

  getItemFeatures() {
    this.itemService.getItemFeatures(this.item.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
      this.item.itemItemFeatures = [];
    })
  }

  addFeature() {
    if (this.itemFeature !== undefined) {
      this.item.itemItemFeatures.push({
        item: {itemId: this.item.itemId},
        itemFeature: this.itemFeature
      })
      this.baManageFormItemFeatureExs.resetForm()
      this.itemFeature = undefined;
    }
  }

  addFeatureTemp() {
    this.newItemFeaturesTemp.push(
      {
        itemFeatureId: 0,
        name: this.newItemFeature
      }
    );
    this.newItemFeature = '';
    this.baManageFormItemFeature.resetForm()
  }

  addNewItemFeature() {
    for (let itemFeature of this.newItemFeaturesTemp) {
      this.item.itemItemFeatures.push({
        item: {itemId: this.item.itemId},
        itemFeature: itemFeature
      })
    }
    this.isNewFeature = false;
    this.newItemFeaturesTemp = [];
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }

  pondHandleAddFile(event) {
    this.item.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.item.itemImgsRaw.length; i++) {
      if (this.item.itemImgsRaw[i].name === event.file.file.name) {
        this.item.itemImgsRaw.splice(i, 1);
      }
    }
  }

  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getIndex(value) {
    return value;
  }

  confirmation = {
    reply: false,
    message: ''
  };

  // confirmationSub = new Subject();

  setDialogBox(message, reply = false) {
    this.confirmation.reply = reply;
    this.confirmation.message = message;
  }

  removeItem(itemId) {
    this.itemService.removeItem(itemId).subscribe((reply) => {
      if (reply) {
        this.items.splice(this.itemIndex, 1);
        if (document.getElementById('item-close-btn') !== null) {
          document.getElementById('item-close-btn').click()
        }
        this.setDialogBox('Item has been removed', true);
      }
    }, (error) => {
      this.setDialogBox(error.error, true);
    })
  }
}
