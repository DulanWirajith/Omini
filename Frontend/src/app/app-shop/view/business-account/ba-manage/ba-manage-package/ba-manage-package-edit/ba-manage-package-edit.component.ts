import {Component, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-ba-manage-package-edit',
  templateUrl: './ba-manage-package-edit.component.html',
  styleUrls: ['./ba-manage-package-edit.component.css']
})
export class BaManagePackageEditComponent implements OnInit {

  newPackageFeature;
  newPackageFeaturesTemp;
  isNewFeature;
  @Input() itemPackages = [];
  itemPackageItemPackageFeatures = [];
  itemPackageItemPackageFeature;
  itemPackageFeature;
  itemPackage;
  businessCategories = [];
  @ViewChild('baManageFormPackageExs', {static: true}) public baManageFormPackageExs: NgForm;
  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.itemPackage = this.itemService.getNewPackage();
    businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  ngOnInit(): void {
  }

  addFeature() {
    if (this.itemPackageFeature !== undefined) {
      this.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemId: this.itemPackage.itemPackageId},
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
    this.baManageFormPackageExs.resetForm()
  }

  addNewPackageFeature() {
    for (let itemPackageFeature of this.newPackageFeaturesTemp) {
      this.itemPackageFeature.itemItemFeatures.push({
        itemPackage: {itemPackageId: this.itemPackage.itemPackageId},
        itemPackageFeature: itemPackageFeature
      })
    }
    this.isNewFeature = false;
    this.newPackageFeaturesTemp = [];
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
