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

  @Output() packageItemItems = [];
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
  selectedPackageItem;

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
      // console.log(packageItem)
      this.packageItem = packageItem.packageItem;
      this.packageItems = packageItem.packageItems;
      this.itemIndex = packageItem.index;
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
      // console.log(this.packageItem.packageItemItems[i])
      if (this.packageItem.packageItemItems[i].packageItem === undefined) {
        this.packageItem.packageItemItems[i] = {
          item: this.packageItem.packageItemItems[i],
          packageItem: {
            packageItemId: this.packageItem.packageItemId
          },
          quantity: this.packageItem.packageItemItems[i].quantity
        }
      }
      this.packageItem.packageItemItems[i].item.item = undefined;
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
    // console.log(this.packageItem)
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
      // console.log(this.packageItem)
      // if (itemPackageR.packageItemItems.length > 0) {
      //   this.packageItem.packageItemItems = this.packageItem.packageItemItems.concat(itemPackageR.packageItemItems);
      // }
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

  addPackageItem() {
    // this.selectedPackageItem.item.itemPackage=undefined
    this.packageItem.packageItemItems.push(this.selectedPackageItem)
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
      for (let i = 0; i < items.length; i++) {
        items[i].item = items[i]
      }
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
    this.itemService.getItemPackageReviews(this.packageItem.packageItemId, customerId).subscribe((itemPackageReview) => {
      this.itemPackageReview = itemPackageReview;
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

  setItemsToItemAdd(packageItems) {
    this.itemService.packageItemsSub.next(packageItems);
  }

  getItemSelected(item) {
    // console.log(this.packageItem.packageItemItems)
    let index: any = this.packageItem.packageItemItems.findIndex(itemObj => {
      return itemObj.itemId === item.itemId
    })
    // console.log(this.items[index])
    this.packageItem.packageItemItems[index].item.itemPackage.itemImgsRaw = [];
    if (this.packageItem.packageItemItems[index].item.itemPackage.itemPackageItemPackageFeatures === undefined) {
      this.itemService.getItemPackageSelected(item.itemId, 'Item').subscribe((itemPackage) => {
        // Object.assign(this.items[index], item)
        // itemPackage.item.itemPackage.itemImgsRaw = [];
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
        this.packageItem.packageItemItems[index].item.itemPackage.itemImgsRaw = [];
        this.packageItem.packageItemItems[index].item.itemPackage.itemPackageItemPackageFeatures = itemPackage.item.itemPackage.itemPackageItemPackageFeatures;
        this.itemService.itemFeaturesSub.next(itemPackage.item.itemFeatures);
        this.itemService.packageItemEditSub.next({
          packageItems: this.packageItem.packageItemItems,
          packageItem: this.packageItem.packageItemItems[index].item,
          index: index,
          editForm: true
        });
        //console.log(this.items[index])
      })
    } else {
      this.itemService.packageItemEditSub.next({
        packageItems: this.packageItem.packageItemItems,
        packageItem: this.packageItem.packageItemItems[index].item,
        index: index,
        editForm: true
      });
    }
  }
}
