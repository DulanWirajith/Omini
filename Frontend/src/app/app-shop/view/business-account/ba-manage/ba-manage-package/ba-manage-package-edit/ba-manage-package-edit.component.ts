import {Component, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {environment} from "../../../../../../../environments/environment";
import {BusinessAccountService} from "../../../../../_service/business-account.service";
import {ItemService} from "../../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import * as $ from "jquery";
import {ShopCartService} from "../../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../../_service/login.service";

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
  @Input() packageItems = [];
  itemPackageItemPackageFeatures = [];
  itemPackageItemPackageFeature;
  itemPackageFeature;
  packageItemCur;
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

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    // this.itemPackage = this.itemService.getNewPackage();
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getItems(businessCategoryId)
      this.getItemPackageFeatures(businessCategoryId)
    })
  }

  ngOnInit(): void {
    // this.btnCreateFeature();
    this.businessCategories = this.businessAccountService.businessCategories;
    // this.getItems(this.packageItemCur)
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getItems(this.businessAccountService.businessCategory.businessCategoryId)
      this.getItemPackageFeatures(this.businessAccountService.businessCategory.businessCategoryId)
    }
    this.toggleCategoryBtn();
  }

  onSubmit(packageItem, imageInput, index) {
    packageItem.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };
    for (let i = 0; i < packageItem.packageItemItems.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (packageItem.packageItemItems[i].packageItem === undefined) {
        packageItem.packageItemItems[i] = {
          item: packageItem.packageItemItems[i],
          packageItem: {
            packageItemId: packageItem.packageItemId
          }
        }
      }
    }
    for (let i = 0; i < packageItem.itemPackage.itemPackageItemPackageFeatures.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (packageItem.itemPackage.itemPackageItemPackageFeatures[i].itemPackageFeature === undefined) {
        packageItem.itemPackageItemPackageFeatures[i] = {
          itemPackageFeature: packageItem.itemPackageItemPackageFeatures[i],
          packageItem: {
            itemPackageId: packageItem.packageItemId
          }
        }
      }
    }
    //console.log(itemPackage)
    const uploadImageData = new FormData();
    for (let itemPackageImage of packageItem.itemPackage.itemImgsRaw) {
      uploadImageData.append('imageFile', itemPackageImage, itemPackageImage.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(packageItem)],
      {
        type: "application/json"
      }));
    this.itemService.updatePackage(uploadImageData, packageItem.packageItemId).subscribe((itemPackageR) => {
      // this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      // this.itemPackage.itemItemPackages = [];
      // this.item = undefined;
      imageInput.removeFiles();
      packageItem.itemPackage.itemPackageImages = packageItem.itemPackage.itemPackageImages.concat(itemPackageR.itemPackage.itemPackageImages);
      packageItem.itemPackage.itemPkgImgs = [];
      if (document.getElementById('btnPackage' + index) !== null) {
        document.getElementById('btnPackage' + index).click()
      }
      packageItem.isNewPackage = false;
    })
  }

  addFeature(packageItem) {
    if (this.itemPackageFeature !== undefined) {
      packageItem.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: packageItem.packageItemId},
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
      this.packageItemCur.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.packageItemCur.packageItemId},
        itemPackageFeature: itemPackageFeature
      })
    }
    this.isNewFeature = false;
    this.newPackageFeaturesTemp = [];
    //console.log(this.packageItemCur)
  }

  getItems(businessCategoryId, packageItem?) {
    //console.log(this.businessProfileCategory)
    // if (this.businessProfileCategory !== undefined) {
    // console.log(5)
    this.itemService.getItemsBusinessCategory(this.loginService.getUser().userId, businessCategoryId).subscribe((items) => {
      // console.log(items)
      this.itemsToAdd = items;
      if (packageItem !== undefined) {
        if (packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId === packageItem.itemPackage.tempBusinessCategory.businessCategoryId) {
          // console.log(itemPackage.tempItems)
          packageItem.packageItemItems = packageItem.tempItems;
          packageItem.itemPackageItemPackageFeatures = packageItem.tempItemFeatures;
        } else {
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

  toggleCategoryBtn() {
    let that = this;
    $(document).on('click', '.btnEdit', function () {
      if (!$(this, '.accordionEdit').hasClass('show')) {
        that.getPackageItemSelected(that, this);
      }
    })
    $(document).on('click', '.btnView', function () {
      if (!$(this, '.accordionView').hasClass('show')) {
        that.getPackageItemSelected(that, this);
      }
    })
    $(document).on('click', '.createFeature', function () {
      that.packageItemCur = that.packageItems[$(this).val()];
    })
  }

  getPackageItemSelected(that, obj) {
    let index: any = that.packageItems.findIndex(packageItem => {
      return packageItem.packageItemId === $(obj).val()
    })
    //console.log($(obj).val())
    // if (that.packageItems[index] !== undefined && that.packageItems[index].itemItemPackages === undefined) {
    that.itemService.getItemPackageSelected($(obj).val(),'Package').subscribe((itemPackage) => {
      // console.log(index)
      // that.categories[index] = category;
      Object.assign(that.packageItems[index], itemPackage.packageItem)
      // that.getItems(that.packageItems[index])
      // that.getItemPackageFeatures(that.packageItems[index])
      that.packageItems[index].itemPackage.itemImgsRaw = [];
      that.packageItems[index].tempBusinessCategory = itemPackage.packageItem.itemPackage.businessProfileCategory.businessCategory;
      that.packageItems[index].items = [];
      for (let item of itemPackage.packageItem.packageItemItems) {
        // console.log(item)
        that.packageItems[index].items.push(item.item);
      }
      that.packageItems[index].tempItems = itemPackage.packageItem.packageItemItems;
      // console.log(that.packageItems[index])
      // for (let i = 0; i < that.categories.length; i++) {
      //   if (that.categories[i].itemCategoryId === $(obj).val()) {
      //     // console.log(category)
      //     that.categoryE = category;
      //     that.categories[i].items = category.items;
      //   }
      // }
    })
    // }
  }

  getImageSrc(itemPackageImg) {
    // console.log(environment.image_url + itemPackageImg.imageName)
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.imageName);
  }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.imageName);
  }

  pondHandleAddFile(event, itemPackageE?) {
    itemPackageE.itemPkgImgs.push(event.file.file);
  }

  pondHandlerRemoveFile(event, itemPackageE?) {
    for (let i = 0; i < itemPackageE.itemPkgImgs.length; i++) {
      if (itemPackageE.itemPkgImgs[i].name === event.file.file.name) {
        itemPackageE.itemPkgImgs.splice(i, 1);
      }
    }
  }

  confirmation = {
    reply: false,
    message: ''
  };

  packageIndex;
  packageE;

  // confirmationSub = new Subject();

  setPackage(packageE, packageIndex) {
    this.packageE = packageE
    this.packageIndex = packageIndex;
    this.setDialogBox('Do you want to remove <b>' + packageE.name + '</b> ?', false)
  }

  setDialogBox(message, reply = false) {
    this.confirmation.reply = reply;
    this.confirmation.message = message;
  }

  removePackage() {
    this.itemService.removePackage(this.packageE.itemPackageId).subscribe((reply) => {
      if (reply) {
        this.packageItems.splice(this.packageIndex, 1);
        this.setDialogBox('Package has been removed', true);
      }
    }, (error) => {
      this.setDialogBox(error.error, true);
    })
  }
}
