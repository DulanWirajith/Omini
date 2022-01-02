import {Component, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import * as $ from "jquery";

@Component({
  selector: 'app-ba-manage-package-edit',
  templateUrl: './ba-manage-package-edit.component.html',
  styleUrls: ['./ba-manage-package-edit.component.css']
})
export class BaManagePackageEditComponent implements OnInit {

  newPackageFeature;
  newPackageFeaturesTemp = [];
  isNewFeature;
  itemPackageFeatures = [];
  @Input() itemPackages = [];
  itemPackageItemPackageFeatures = [];
  itemPackageItemPackageFeature;
  itemPackageFeature;
  itemPackageCur;
  businessCategories = [];
  businessProfileCategory;
  itemsToAdd;
  @ViewChild('baManageFormPackageExs', {static: true}) public baManageFormPackageExs: NgForm;
  @ViewChild('baManageFormPackageFeature', {static: true}) public baManageFormPackageFeature: NgForm;
  @ViewChild('imageInput') imageInput: any;
  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    // this.itemPackage = this.itemService.getNewPackage();
    businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  ngOnInit(): void {
    this.btnCreateFeature();
  }

  onSubmit(itemPackage) {
    itemPackage.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };
    for (let i = 0; i < itemPackage.itemItemPackages.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (itemPackage.itemItemPackages[i].itemPackage === undefined) {
        itemPackage.itemItemPackages[i] = {
          item: itemPackage.itemItemPackages[i],
          itemPackage: {
            itemPackageId: itemPackage.itemPackageId
          }
        }
      }
    }
    for (let i = 0; i < itemPackage.itemPackageItemPackageFeatures.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (itemPackage.itemPackageItemPackageFeatures[i].itemPackageFeature === undefined) {
        itemPackage.itemItemPackages[i] = {
          itemPackageFeature: itemPackage.itemPackageItemPackageFeatures[i],
          itemPackage: {
            itemPackageId: itemPackage.itemPackageId
          }
        }
      }
    }
    console.log(itemPackage)
    const uploadImageData = new FormData();
    for (let itemImg of itemPackage.itemPkgImgs) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(itemPackage)],
      {
        type: "application/json"
      }));
    this.itemService.updatePackage(uploadImageData, itemPackage.itemPackageId).subscribe((itemPackageR) => {
      // this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      // this.itemPackage.itemItemPackages = [];
      // this.item = undefined;
      this.imageInput.removeFiles();
      itemPackage.itemPackageImgs = itemPackage.itemPackageImgs.concat(itemPackageR.itemPackageImgs);
    })
  }

  addFeature(itemPackage) {
    if (this.itemPackageFeature !== undefined) {
      itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemId: itemPackage.itemPackageId},
        itemPackageFeature: this.itemPackageFeature
      })
      this.baManageFormPackageExs.resetForm()
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
      this.itemPackageCur.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.itemPackageCur.itemPackageId},
        itemPackageFeature: itemPackageFeature
      })
    }
    this.isNewFeature = false;
    this.newPackageFeaturesTemp = [];
    console.log(this.itemPackageCur)
  }

  getItems(itemPackage?) {
    console.log(this.businessProfileCategory)
    // if (this.businessProfileCategory !== undefined) {
    this.itemService.getItemsBusinessCategory("B321", itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
      // console.log(items)
      this.itemsToAdd = items;
      if (itemPackage !== undefined) {
        if (itemPackage.businessProfileCategory.businessCategory.businessCategoryId === itemPackage.tempBusinessCategory.businessCategoryId) {
          // console.log(itemPackage.tempItems)
          itemPackage.itemItemPackages = itemPackage.tempItems;
        } else {
          itemPackage.itemItemPackages = [];
        }
        // itemPackage.items = [];
        // for (let item of itemPackage.itemItemPackages) {
        //   itemPackage.items.push(item.item);
        // }
      }
    })
    // }
  }

  getItemPackageFeatures(itemPackage) {
    if (itemPackage.businessProfileCategory !== undefined) {
      this.itemService.getItemPackageFeatures(itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemPackageFeatures) => {
        // console.log(items)
        this.itemPackageFeatures = itemPackageFeatures;
      })
    }
  }

  btnCreateFeature() {
    let that = this;
    $(document).on('click', '.createFeature', function () {
      that.itemPackageCur = that.itemPackages[$(this).val()];
    })
  }

  getImageSrc(itemPackageImg) {
    // console.log(itemImg)
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }
}
