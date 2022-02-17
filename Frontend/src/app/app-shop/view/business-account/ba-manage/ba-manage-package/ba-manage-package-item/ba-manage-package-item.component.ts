import {Component, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../../../../../_service/item.service";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";
import {LoginService} from "../../../../../../_service/login.service";

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

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private loginService: LoginService) {
    this.item = this.itemService.getNewItem();
    this.item.packageOnly = true;
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    this.itemService.packageItemsSub.subscribe((packageItems) => {
      this.packageItems = packageItems;
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
      this.packageItems.push({
        itemId: item.itemId,
        name: item.itemPackage.name,
        item: item
      })
      console.log(this.packageItems)
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
}
