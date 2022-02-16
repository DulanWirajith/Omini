import {Component, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import * as $ from "jquery";
import {ShopCartService} from "../../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../../_service/login.service";
import {Lightbox} from "ngx-lightbox";

@Component({
  selector: 'app-ba-manage-package-edit',
  templateUrl: './ba-manage-package-edit.component.html',
  styleUrls: ['./ba-manage-package-edit.component.css']
})
export class BaManagePackageEditComponent implements OnInit {


  itemIndex;
  items;
  packageItem;
  packageItems;
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
  newPackageFeature;
  newPackageFeaturesTemp = [];
  @ViewChild('baManageFormPackageFeatureExs', {static: true}) public baManageFormPackageFeatureExs: NgForm;
  @ViewChild('baManageFormPackageFeature', {static: true}) public baManageFormPackageFeature: NgForm;
  _album: [];
  backBtn = false;
  packageItemCur;
  itemsToAdd = [];
  itemPackageFeatures = [];

  // lightbox;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private loginService: LoginService) {
    this.packageItem = this.itemService.getNewPackage();
    // this.lightbox = _lightbox;
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    this.itemService.packageItemSub.observers = [];
    this.itemService.packageItemSub.subscribe((packageItem) => {
      // console.log(item)
      this.packageItem = packageItem.packageItem;
      this.packageItems = packageItem.packageItems;
      this.itemIndex = packageItem.index
      this.getAlbum(this.packageItem);
      this.packageItem.itemPackage.tempBusinessCategory = this.packageItem.itemPackage.businessProfileCategory.businessCategory;
      this.getItems(this.packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId)
      // this.backBtn=true
      let yourElem = <HTMLElement>document.querySelector('.package-viewer-g-btn');
      if (yourElem !== null) {
        yourElem.click();
        // if (this.review) {
        this.itemPackageObj.backBtn = undefined;
        // }
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

  onSubmit() {
    this.packageItem.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };
    for (let i = 0; i < this.packageItem.packageItemItems.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (this.packageItem.packageItemItems[i].packageItem === undefined) {
        this.packageItem.packageItemItems[i] = {
          item: this.packageItem.packageItemItems[i],
          packageItem: {
            packageItemId: this.packageItem.packageItemId
          }
        }
      }
    }
    for (let i = 0; i < this.packageItem.itemPackage.itemPackageItemPackageFeatures.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (this.packageItem.itemPackage.itemPackageItemPackageFeatures[i].itemPackageFeature === undefined) {
        this.packageItem.itemPackageItemPackageFeatures[i] = {
          itemPackageFeature: this.packageItem.itemPackageItemPackageFeatures[i],
          packageItem: {
            itemPackageId: this.packageItem.packageItemId
          }
        }
      }
    }
    //console.log(itemPackage)
    const uploadImageData = new FormData();
    for (let itemPackageImage of this.packageItem.itemPackage.itemImgsRaw) {
      uploadImageData.append('imageFile', itemPackageImage, itemPackageImage.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(this.packageItem)],
      {
        type: "application/json"
      }));
    this.itemService.updatePackage(uploadImageData, this.packageItem.packageItemId).subscribe((itemPackageR) => {
      // this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      // this.itemPackage.itemItemPackages = [];
      // this.item = undefined;
      this.imageInput.removeFiles();
      console.log(itemPackageR)
      if (itemPackageR.packageItemItems.length > 0) {
        this.packageItem.packageItemItems = this.packageItem.packageItemItems.concat(itemPackageR.packageItemItems);
      }
      this.packageItem.itemPackage.itemPackageImages = this.packageItem.itemPackage.itemPackageImages.concat(itemPackageR.itemPackage.itemPackageImages);
      this.packageItem.itemPackage.itemImgsRaw = [];
      if (document.getElementById('package-back-btn') !== null) {
        document.getElementById('package-back-btn').click();
      }
      this.packageItem.isNewPackage = false;
      this.getAlbum(this.packageItem);
    })
  }

  addFeature() {
    if (this.itemPackageFeature !== undefined) {
      this.packageItem.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.packageItem.packageItemId},
        itemPackageFeature: this.itemPackageFeature
      })
      // this.baManageFormPackageExs.resetForm()
      this.itemPackageFeature = undefined;
    }
  }

  addFeatureTemp() {
    this.newPackageFeaturesTemp.push(
      {
        itemPackageFeatureId: 0,
        name: this.newPackageFeature
      }
    );
    this.newPackageFeature = '';
    this.baManageFormPackageFeature.resetForm()
  }

  addNewPackageFeature() {
    for (let itemPackageFeature of this.newPackageFeaturesTemp) {
      this.packageItem.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.packageItem.itemId},
        itemPackageFeature: itemPackageFeature
      })
    }
    this.isNewFeature = false;
    this.newPackageFeaturesTemp = [];
  }

  getItems(businessCategoryId, packageItem?) {
    //console.log(this.businessProfileCategory)
    // if (this.businessProfileCategory !== undefined) {
    // console.log(5)
    this.itemService.getItemsBusinessCategory(this.loginService.getUser().userId, businessCategoryId).subscribe((items) => {
      // console.log(items)
      this.itemsToAdd = items;
      if (packageItem !== undefined) {
        // console.log(packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId === packageItem.itemPackage.tempBusinessCategory.businessCategoryId)
        if (packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId === packageItem.itemPackage.tempBusinessCategory.businessCategoryId) {
          // console.log(itemPackage.tempItems)
          packageItem.packageItemItems = packageItem.tempItems;
          packageItem.itemPackageItemPackageFeatures = packageItem.tempItemFeatures;
        } else {
          packageItem.tempItems = packageItem.packageItemItems;
          packageItem.packageItemItems = [];
          packageItem.itemPackageItemPackageFeatures = [];
        }
        // itemPackage.items = [];
        // for (let item of itemPackage.itemItemPackages) {
        //   itemPackage.items.push(item.item);
        // }
      }
    })
    // }
  }

  getItemPackageFeatures(businessCategoryId) {
    // if (itemPackage.businessProfileCategory !== undefined) {
    this.itemService.getItemPackageFeatures(businessCategoryId).subscribe((itemPackageFeatures) => {
      // console.log(items)
      this.itemPackageFeatures = itemPackageFeatures;
    })
    // }
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  pondHandleAddFile(event) {
    this.packageItem.itemPackage.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.packageItem.itemPackage.itemImgsRaw.length; i++) {
      if (this.packageItem.itemPackage.itemImgsRaw[i].name === event.file.file.name) {
        this.packageItem.itemPackage.itemImgsRaw.splice(i, 1);
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
  itemPackageReviews = [];

  getItemPackageReviews() {
    this.review = true;
    let customerId = '0';
    if (this.getUser() !== null) {
      customerId = this.getUser().userId;
    }
    this.itemService.getItemPackageReviews(this.packageItem.itemId, customerId).subscribe((itemPackageReviews) => {
      this.itemPackageReviews = itemPackageReviews;
      // console.log(this.itemPackageReviews)
    })
  }

  itemPackageObj = {
    itemPackage: undefined,
    backBtn: undefined
  };
  prevPackageItem;

  getItemPackageSelected(prevItemPackage, itemPackage) {
    // this.itemService.itemPackageSub.next({
    //   itemPackage: itemPackage,
    //   backBtn: 'item-package-viewer-g'
    // });

    this.packageItem = itemPackage;
    this.prevPackageItem = prevItemPackage;
    this.getAlbum(this.packageItem);
    this.itemPackageObj.backBtn = true;
  }

  goBack() {
    this.packageItem = this.prevPackageItem;
    this.itemPackageObj.backBtn = undefined;
    this.getAlbum(this.prevPackageItem);
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}
