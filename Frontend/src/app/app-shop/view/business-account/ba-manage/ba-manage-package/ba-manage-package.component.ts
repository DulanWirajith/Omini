import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import {ItemService} from "../../../../_service/item.service";
import * as $ from "jquery";
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-ba-manage-package',
  templateUrl: './ba-manage-package.component.html',
  styleUrls: ['./ba-manage-package.component.css']
})
export class BaManagePackageComponent implements OnInit {

  itemPackage;
  itemPackages = [];
  items = [];
  itemPackageItemPackageFeatures = [];
  itemPackageItemPackageFeature;
  newPackageFeature;
  newPackageFeaturesTemp;
  isNewFeature;
  itemPackageFeature;
  // item;
  // packageImg;
  // imgUrl;
  // itemsAdded;
  itemsToAdd = [];
  itemsToAddE = [];
  businessCategories = [];
  // isNewPackage = false;
  businessProfileCategory;
  // itemPkgImgs = [];
  // itemPkgImgsE = [];
  @ViewChild('baManageFormPackage', {static: true}) public baManageFormPackage: NgForm;
  @ViewChild('baManageFormPackageExs', {static: true}) public baManageFormPackageExs: NgForm;
  @ViewChild('imageInput') imageInput: any;

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  // @ViewChild('baManageFormPackageItemExs', {static: true}) public baManageFormPackageItemExs: NgForm;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.itemPackage = this.itemService.getNewPackage();
    businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  ngOnInit(): void {
    // this.getItems();
    // this.getBusinessCategories();
    //console.log(9)
    this.businessCategories = this.businessAccountService.businessCategories;
    this.toggleCategoryBtn();
  }

  // getBusinessCategories() {
  //   this.businessCategories = this.businessAccountService.businessCategories;
  // }

  // addItem() {
  // if (this.item !== undefined) {
  //   this.package.itemItemPackages.push({
  //     item: this.item
  //   })
  //   this.baManageFormPackageItemExs.resetForm()
  //   this.item = undefined;
  // }
  // }
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

  onSubmit() {
    // let packageItems = [];
    // for (let packageItem of this.package.itemItemPackages) {
    //   packageItems.push({
    //     item: packageItem
    //   })
    // }
    // this.package.itemItemPackages = packageItems;

    this.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    let itemItemPackages = [];
    for (let i = 0; i < this.itemPackage.itemItemPackages.length; i++) {
      if (this.itemPackage.itemItemPackages[i].itemPackage === undefined) {
        this.itemPackage.itemItemPackages[i] = {
          name: this.itemPackage.itemItemPackages[i].itemTitle,
          item: this.itemPackage.itemItemPackages[i],
          itemPackage: {
            itemPackageId: this.itemPackage.itemPackageId
          }
        }
      }
    }

    // this.itemPackage.itemItemPackages = itemItemPackages;
    console.log(this.itemPackage)
    const uploadImageData = new FormData();
    for (let itemImg of this.itemPackage.itemPkgImgs) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(this.itemPackage)],
      {
        type: "application/json"
      }));
    this.itemService.addPackage(uploadImageData).subscribe((itemPackage) => {
      this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      this.itemPackage.itemItemPackages = [];
      // this.item = undefined;

      this.itemPackages.push(itemPackage)
      // this.item.itemItemFeatures = [];
      this.imageInput.removeFiles();
    })
  }

  onSubmitE(itemPackageE, imageInput) {
    itemPackageE.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };
    for (let i = 0; i < itemPackageE.itemItemPackages.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (itemPackageE.itemItemPackages[i].itemPackage === undefined) {
        itemPackageE.itemItemPackages[i] = {
          item: itemPackageE.itemItemPackages[i],
          itemPackage: {
            itemPackageId: itemPackageE.itemPackageId
          }
        }
      }
    }
    console.log(itemPackageE)
    const uploadImageData = new FormData();
    for (let itemImg of itemPackageE.itemPkgImgs) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(itemPackageE)],
      {
        type: "application/json"
      }));
    this.itemService.updatePackage(uploadImageData, itemPackageE.itemPackageId).subscribe((itemPackageR) => {
      // this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      // this.itemPackage.itemItemPackages = [];
      // this.item = undefined;
      imageInput.removeFiles();
      itemPackageE.itemPackageImgs = itemPackageE.itemPackageImgs.concat(itemPackageR.itemPackageImgs);
    })
  }

  getItemPackagesOrdered() {
    if (this.businessProfileCategory !== null) {
      this.itemService.getItemPackagesOrdered("B321", this.businessProfileCategory.businessCategoryId).subscribe((packages) => {
        // console.log(packages)
        this.itemPackages = packages;
        for (let itemPackage of this.itemPackages) {
          itemPackage.itemPkgImgs = [];
          itemPackage.businessProfileCategory = {
            businessProfile: undefined,
            businessCategory: undefined
          }
          itemPackage.isUpdatePackage = false;
        }
      })
    } else {
      this.itemPackages = [];
    }
  }

  getItems(val, itemPackage?) {
    if (val === 'n') {
      if (this.itemPackage.businessProfileCategory !== null) {
        this.itemService.getItemsBusinessCategory("B321", this.itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
          // console.log(items)
          this.itemsToAdd = items;
        })
      }
    } else if (val === 'e') {
      if (this.businessProfileCategory !== null) {
        this.itemService.getItemsBusinessCategory("B321", this.businessProfileCategory.businessCategoryId).subscribe((items) => {
          // console.log(items)
          this.itemsToAddE = items;
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
      }
    }
  }

  getImageSrc(itemPackageImg) {
    // console.log(itemImg)
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

  toggleCategoryBtn() {
    let that = this;
    $(document).on('click', '.btnEdit', function () {
      if (!$(this, '.accordionEdit').hasClass('show')) {
        that.getItemPackageSelected(that, this);
      }
    })
    $(document).on('click', '.btnView', function () {
      if (!$(this, '.accordionView').hasClass('show')) {
        that.getItemPackageSelected(that, this);
      }
    })
  }

  getItemPackageSelected(that, obj) {
    let index: any = that.itemPackages.findIndex(itemPackage => {
      return itemPackage.itemPackageId === $(obj).val()
    })
    console.log($(obj).val())
    if (that.itemPackages[index] !== undefined && that.itemPackages[index].itemItemPackages === undefined) {
      that.itemService.getItemPackageSelected($(obj).val()).subscribe((itemPackage) => {
        // that.categories[index] = category;
        Object.assign(that.itemPackages[index], itemPackage)
        that.itemPackages[index].tempBusinessCategory = itemPackage.businessProfileCategory.businessCategory;
        that.itemPackages[index].items = [];
        for (let item of itemPackage.itemItemPackages) {
          that.itemPackages[index].items.push(item.item);
        }
        that.itemPackages[index].tempItems = itemPackage.itemItemPackages;
        // console.log(that.itemPackages[index])
        // for (let i = 0; i < that.categories.length; i++) {
        //   if (that.categories[i].itemCategoryId === $(obj).val()) {
        //     // console.log(category)
        //     that.categoryE = category;
        //     that.categories[i].items = category.items;
        //   }
        // }
      })
    }
  }

  // setItems(itemPackage, items) {
  //   // this.items = [];
  //   for (let item of itemPackage.itemItemPackages) {
  //     items.push(item.item);
  //   }
  // }

  // setItemAvailable(item) {
  //   this.itemService.setItemAvailable(item.itemId).subscribe((reply) => {
  //     item.itemAvailable = reply;
  //   })
  // }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

  // getImageSrc(itemImg) {
  //   let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
  //   return this.sanitizer.bypassSecurityTrustUrl(imageData);
  // }

  pondHandleAddFile(event, val, itemPackageE?) {
    if (val === 'n') {
      this.itemPackage.itemPkgImgs.push(event.file.file);
    } else if (val === 'e') {
      itemPackageE.itemPkgImgs.push(event.file.file);
    }
  }

  pondHandlerRemoveFile(event, val, itemPackageE?) {
    if (val === 'n') {
      for (let i = 0; i < this.itemPackage.itemPkgImgs.length; i++) {
        // console.log(this.itemPackage.itemPkgImgs[i].name + ' ' + event.file.file.name)
        if (this.itemPackage.itemPkgImgs[i].name === event.file.file.name) {
          this.itemPackage.itemPkgImgs.splice(i, 1);
        }
      }
    } else if (val === 'e') {
      for (let i = 0; i < itemPackageE.itemPkgImgs.length; i++) {
        if (itemPackageE.itemPkgImgs[i].name === event.file.file.name) {
          itemPackageE.itemPkgImgs.splice(i, 1);
        }
      }
    }
  }
}
