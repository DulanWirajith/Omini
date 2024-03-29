import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {Lightbox} from 'ngx-lightbox';
import {ShopCartService} from "../../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../../_service/login.service";
import * as $ from "jquery";

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
  itemPackageFeature;
  isNewFeature;
  newItemFeature;
  newItemFeaturesTemp = [];
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  _album: [];
  backBtn = false;
  packageOnly;

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
      // console.log(item)
      this.packageOnly = item.item.packageOnly;
      this.item = item.item;
      this.items = item.items;
      this.itemIndex = item.index
      this.getAlbum(this.item);
      let yourElem = <HTMLElement>document.querySelector('.item-viewer-g-btn');
      if (yourElem !== null) {
        yourElem.click();
      }
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
    imageList.itemPackage.itemPackageImages.forEach(element => {
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
        src: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.imageName),
        caption: img.itemImgId,
        thumb: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.imageName)
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
    this.item.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };
    if (this.item.itemPackage.discount === 'N/A') {
      this.item.itemPackage.discount = 0;
    }
    // for (let item of this.item.itemItemFeatures) {
    //   item.name = item.item.name;
    // }
    //console.log(this.item)
    const uploadImageData = new FormData();
    for (let itemPackageImage of this.item.itemPackage.itemImgsRaw) {
      uploadImageData.append('imageFile', itemPackageImage, itemPackageImage.name);
    }
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.itemService.updateItem(uploadImageData, this.item.itemId).subscribe((item) => {
      this.imageInput.removeFiles();
      // this.item.itemPackage.itemImgsRaw = [];
      this.item.itemPackage.itemPackageImages = this.item.itemPackage.itemPackageImages.concat(item.itemPackage.itemPackageImages);
      if (document.getElementById('item-back-btn') !== null) {
        document.getElementById('item-back-btn').click();
      }
      this.item.isNewItem = false;
      this.item.itemPackage.discountedPrice = item.itemPackage.discountedPrice;
      this.getAlbum(this.item);
      if (this.item.packageOnly !== this.packageOnly) {
        this.items.splice(this.itemIndex, 1);
      }
    })
  }

  getItemFeatures() {
    this.itemService.getItemPackageFeatures(this.item.itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
      this.item.itemPackage.itemPackageItemPackageFeatures = [];
    })
  }

  addFeature() {
    if (this.itemPackageFeature !== undefined) {
      this.item.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.item.itemId},
        itemPackageFeature: this.itemPackageFeature
      })
      this.baManageFormItemFeatureExs.resetForm()
      this.itemPackageFeature = undefined;
    }
  }

  addFeatureTemp() {
    this.newItemFeaturesTemp.push(
      {
        itemPackageFeatureId: 0,
        name: this.newItemFeature
      }
    );
    this.newItemFeature = '';
    this.baManageFormItemFeature.resetForm()
  }

  addNewItemFeature() {
    for (let itemPackageFeature of this.newItemFeaturesTemp) {
      this.item.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.item.itemId},
        itemPackageFeature: itemPackageFeature
      })
    }
    this.isNewFeature = false;
    this.newItemFeaturesTemp = [];
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  pondHandleAddFile(event) {
    this.item.itemPackage.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.item.itemPackage.itemImgsRaw.length; i++) {
      if (this.item.itemPackage.itemImgsRaw[i].name === event.file.file.name) {
        this.item.itemPackage.itemImgsRaw.splice(i, 1);
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

  review = false;
  itemPackageReview = {
    rating1: 0,
    rating2: 0,
    itemPackageReviews: []
  };

  getItemPackageReviews() {
    this.review = true;
    let customerId = '0';
    if (this.getUser() !== null) {
      customerId = this.getUser().userId;
    }
    this.itemService.getItemPackageReviews(this.item.itemId, customerId).subscribe((itemPackageReview) => {
      this.itemPackageReview = itemPackageReview;
      // console.log(this.itemPackageReviews)
    })
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}
