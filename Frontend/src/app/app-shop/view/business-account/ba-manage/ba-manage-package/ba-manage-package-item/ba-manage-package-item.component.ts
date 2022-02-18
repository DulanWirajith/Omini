import {Component, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../../../../../_service/item.service";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {LoginService} from "../../../../../../_service/login.service";
import {environment} from "../../../../../../../environments/environment";

@Component({
  selector: 'app-ba-manage-package-item',
  templateUrl: './ba-manage-package-item.component.html',
  styleUrls: ['./ba-manage-package-item.component.css']
})
export class BaManagePackageItemComponent implements OnInit {

  item;
  businessCategories = [];
  itemFeatures = [];
  isNewFeature = false;
  @ViewChild('baManageFormItem', {static: true}) public baManageFormItem: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  @ViewChild('imageInput') imageInput: any;
  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  };
  newItemFeaturesTemp = [];
  itemPackageFeature;
  newItemFeature;
  packageItems = [];
  itemIndex;
  editForm = false;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private loginService: LoginService, private sanitizer: DomSanitizer) {
    this.item = this.itemService.getNewItem();
    this.item.packageOnly = true;
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    this.itemService.packageItemsSub.subscribe((packageItems) => {
      this.packageItems = packageItems;
    })
    this.itemService.packageItemEditSub.observers = [];
    this.itemService.packageItemEditSub.subscribe((item) => {
      // console.log(item)
      // this.packageOnly = item.item.packageOnly;
      this.editForm = item.editForm;
      this.item = item.packageItem;
      this.packageItems = item.packageItems;
      this.itemIndex = item.index
      // this.getAlbum(this.item);
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

  ngOnInit(): void {
    this.businessCategories = this.businessAccountService.businessCategories;
  }

  getItemFeatures() {
    this.itemService.getItemPackageFeatures(this.item.itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
      this.item.itemPackage.itemPackageItemPackageFeatures = [];
    })
  }

  submitForm() {
    if (this.editForm) {
      this.onSubmitE()
    } else {
      this.onSubmit()
    }
  }

  onSubmit() {
    // console.log(this.item.itemPackage.itemImgsRaw.length)
    this.item.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };

    //console.log(this.item)
    const uploadImageData = new FormData();
    for (let itemPackageImage of this.item.itemPackage.itemImgsRaw) {
      uploadImageData.append('imageFile', itemPackageImage, itemPackageImage.name);
    }
    // let item = {
    //   itemPackage: this.item
    // }
    // console.log(this.item)
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.itemService.addItem(uploadImageData).subscribe((item) => {
      // if (this.item.packageOnly === this.itemType.val) {
      //   this.items.push(item);
      // }
      this.baManageFormItem.resetForm(this.itemService.getNewItem());
      this.item.itemPackage.itemPackageItemPackageFeatures = [];
      this.imageInput.removeFiles();
      if (document.getElementById('btnBackItem') !== null) {
        document.getElementById('btnBackItem').click()
      }
      this.item.isNewItem = false;
      // console.log(this.packageItems)
      this.packageItems.push({
        itemId: item.itemId,
        name: item.itemPackage.name,
        item: item,
        itemPackage: item.itemPackage,
        quantity: 1
      })
      // console.log(this.packageItems)
    })
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
      if (document.getElementById('btnBackItem') !== null) {
        document.getElementById('btnBackItem').click();
      }
      this.item.isNewItem = false;
      this.item.itemPackage.discountedPrice = item.itemPackage.discountedPrice;
      // this.getAlbum(this.item);
      // if (this.item.packageOnly !== this.packageOnly) {
      //   this.items.splice(this.itemIndex, 1);
      // }
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
    // console.log(this.item.itemPackage.itemPackageItemPackageFeatures)
    this.isNewFeature = false;
    this.newItemFeaturesTemp = [];
  }

  // @ViewChild('baManageFormItemE', {static: true}) public baManageFormItemE: NgForm;

  pondHandleAddFile(event) {
    this.item.itemPackage.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.item.itemPackage.itemImgsRaw.length; i++) {
      // console.log(this.itemImgs[i].name + ' ' + event.file.file.name)
      if (this.item.itemPackage.itemImgsRaw[i].name === event.file.file.name) {
        this.item.itemPackage.itemImgsRaw.splice(i, 1);
      }
    }
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }
}
