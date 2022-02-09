import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";
import {ItemService} from "../../../../_service/item.service";
import * as $ from "jquery";
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from "../../../../../../environments/environment";
import {ShopCartService} from "../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../_service/login.service";

@Component({
  selector: 'app-ba-manage-package',
  templateUrl: './ba-manage-package.component.html',
  styleUrls: ['./ba-manage-package.component.css']
})
export class BaManagePackageComponent implements OnInit {

  packageItem;
  packageItems = [];
  itemPackageFeatures = [];
  items = [];
  itemPackageItemPackageFeatures = [];
  itemPackageItemPackageFeature;
  newPackageFeature;
  newPackageFeaturesTemp = [];
  isNewFeature;
  itemPackageFeature;
  // item;
  // packageImg;
  // imgUrl;
  // itemsAdded;
  itemsToAdd = [];
  businessCategories = [];
  // isNewPackage = false;
  businessProfileCategory;
  // itemPkgImgs = [];
  // itemPkgImgsE = [];
  @ViewChild('baManageFormPackage', {static: true}) public baManageFormPackage: NgForm;
  @ViewChild('baManageFormPackageFeature', {static: true}) public baManageFormPackageFeature: NgForm;
  @ViewChild('baManageFormPackageExs', {static: true}) public baManageFormPackageExs: NgForm;
  @ViewChild('imageInput') imageInput: any;

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  // @ViewChild('baManageFormPackageItemExs', {static: true}) public baManageFormPackageItemExs: NgForm;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    this.packageItem = this.itemService.getNewPackage();
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
      // console.log(this.businessCategories)
    })
    // this.businessAccountService.businessCategorySub.observers = [];
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getPackageItemsOrdered(businessCategoryId);
    })
  }

  ngOnInit(): void {
    // this.getItems();
    // this.getBusinessCategories();
    // console.log(this.businessAccountService.businessCategory)
    this.businessCategories = this.businessAccountService.businessCategories;
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getPackageItemsOrdered(this.businessAccountService.businessCategory.businessCategoryId);
    }
    // this.toggleCategoryBtn();
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
      this.packageItem.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemId: this.packageItem.itemPackage.itemPackageId},
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
    //console.log(this.newPackageFeaturesTemp)
  }

  addNewPackageFeature() {
    for (let itemPackageFeature of this.newPackageFeaturesTemp) {
      this.packageItem.itemPackage.itemPackageItemPackageFeatures.push({
        itemPackage: {itemPackageId: this.packageItem.itemPackage.itemPackageId},
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

    this.packageItem.itemPackage.businessProfileCategory.businessProfile = {
      businessProId: this.loginService.getUser().userId
    };

    // let itemItemPackages = [];
    // for (let i = 0; i < this.packageItem.itemPackage.packageItemItems.length; i++) {
    //   if (this.packageItem.itemPackage.packageItemItems[i].itemPackage === undefined) {
    //     this.packageItem.itemPackage.packageItemItems[i] = {
    //       name: this.packageItem.itemPackage.packageItemItems[i].name,
    //       item: this.packageItem.itemPackage.packageItemItems[i],
    //       itemPackage: {
    //         itemPackageId: this.packageItem.itemPackage.itemPackageId
    //       }
    //     }
    //   }
    // }
    for (let i = 0; i < this.packageItem.packageItemItems.length; i++) {
      // console.log(itemPackageE.itemItemPackages[i].itemPackage)
      if (this.packageItem.packageItemItems[i].packageItem === undefined) {
        this.packageItem.packageItemItems[i] = {
          name: this.packageItem.packageItemItems[i].name,
          item: this.packageItem.packageItemItems[i],
          packageItem: {
            packageItemId: this.packageItem.packageItemId
          }
        }
      }
    }

    for (let i = 0; i < this.packageItem.itemPackage.itemPackageItemPackageFeatures.length; i++) {
      if (this.packageItem.itemPackage.itemPackageItemPackageFeatures[i].itemPackageFeature === undefined) {
        this.packageItem.itemPackage.itemPackageItemPackageFeatures[i] = {
          name: this.packageItem.itemPackage.itemItemPackages[i].name,
          itemPackageFeature: this.packageItem.itemPackage.itemPackageItemPackageFeatures[i],
          itemPackage: {
            itemPackageId: this.packageItem.itemPackage.itemPackageId
          }
        }
      }
    }

    // this.itemPackage.itemItemPackages = itemItemPackages;
    console.log(this.packageItem)
    const uploadImageData = new FormData();
    for (let itemPackageImage of this.packageItem.itemPackage.itemImgsRaw) {
      uploadImageData.append('imageFile', itemPackageImage, itemPackageImage.name);
    }
    uploadImageData.append('package', new Blob([JSON.stringify(this.packageItem)],
      {
        type: "application/json"
      }));
    this.itemService.addPackage(uploadImageData).subscribe((packageItem) => {
      this.packageItem.itemPackage.itemItemPackages = [];
      this.packageItem.itemPackage.itemPackageItemPackageFeatures = [];
      packageItem.itemPackage.itemPackageImages = this.packageItem.itemPackage.itemPackageImages.concat(packageItem.itemPackage.itemPackageImages);
      packageItem.itemPackage.itemImgsRaw = [];
      // this.item = undefined;

      this.packageItems.push(JSON.parse(JSON.stringify(packageItem)))
      this.baManageFormPackage.resetForm(this.itemService.getNewPackage());
      // this.item.itemItemFeatures = [];
      this.imageInput.removeFiles();
      if (document.getElementById('btnAddPackage') !== null) {
        document.getElementById('btnAddPackage').click()
      }
      this.packageItem.itemPackage.isNewPackage = false;
    })
  }


  getPackageItemsOrdered(businessCategoryId) {
    if (this.businessProfileCategory !== null) {
      this.itemService.getPackageItemsOrdered(this.loginService.getUser().userId, businessCategoryId, 0, 100).subscribe((packages) => {
        // console.log(packages)
        this.packageItems = packages;
        for (let packageItem of this.packageItems) {
          packageItem.itemPackage.itemImgsRaw = [];
          packageItem.itemPackage.businessProfileCategory = {
            businessProfile: undefined,
            businessCategory: undefined
          }
          packageItem.isUpdatePackage = false;
        }
      })
    } else {
      this.packageItems = [];
    }
  }

  getItems() {
    // if (val === 'item') {
    // if (this.itemPackage.businessProfileCategory.businessCategory !== undefined) {
    this.itemService.getItemsBusinessCategory(this.loginService.getUser().userId, this.packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((items) => {
      // console.log(items)
      this.itemsToAdd = items;
      this.packageItem.packageItemItems = [];
    })
    // }
    // }
  }

  getItemPackageFeatures() {
    if (this.packageItem.itemPackage.businessProfileCategory !== undefined) {
      this.itemService.getItemPackageFeatures(this.packageItem.itemPackage.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemPackageFeatures) => {
        // console.log(items)
        this.itemPackageFeatures = itemPackageFeatures;
      })
    }
  }

  getImageSrc(itemPackageImg) {
    // console.log(itemPackageImg)
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.imageName);
  }

  // toggleCategoryBtn() {
  //   let that = this;
  //   $(document).on('click', '.btnEdit', function () {
  //     if (!$(this, '.accordionEdit').hasClass('show')) {
  //       that.getItemPackageSelected(that, this);
  //     }
  //   })
  //   $(document).on('click', '.btnView', function () {
  //     if (!$(this, '.accordionView').hasClass('show')) {
  //       that.getItemPackageSelected(that, this);
  //     }
  //   })
  // }
  //
  // getItemPackageSelected(that, obj) {
  //   let index: any = that.packageItems.findIndex(itemPackage => {
  //     return itemPackage.itemPackageId === $(obj).val()
  //   })
  //   //console.log($(obj).val())
  //   if (that.packageItems[index] !== undefined && that.packageItems[index].itemItemPackages === undefined) {
  //     that.itemService.getItemPackageSelected($(obj).val()).subscribe((itemPackage) => {
  //       // that.categories[index] = category;
  //       Object.assign(that.packageItems[index], itemPackage)
  //       that.packageItems[index].tempBusinessCategory = itemPackage.businessProfileCategory.businessCategory;
  //       that.packageItems[index].items = [];
  //       for (let item of itemPackage.itemItemPackages) {
  //         that.packageItems[index].items.push(item.item);
  //       }
  //       that.packageItems[index].tempItems = itemPackage.itemItemPackages;
  //       // console.log(that.packageItems[index])
  //       // for (let i = 0; i < that.categories.length; i++) {
  //       //   if (that.categories[i].itemCategoryId === $(obj).val()) {
  //       //     // console.log(category)
  //       //     that.categoryE = category;
  //       //     that.categories[i].items = category.items;
  //       //   }
  //       // }
  //     })
  //   }
  // }

  // setItems(itemPackage, items) {
  //   // this.items = [];
  //   for (let item of itemPackage.itemItemPackages) {
  //     items.push(item.item);
  //   }
  // }

  // setItemPackageAvailable(item) {
  //   this.itemService.setItemPackageAvailable(item.itemId).subscribe((reply) => {
  //     item.itemAvailable = reply;
  //   })
  // }

  getPackageImageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemPackageImg.itemPackageImgType + ';base64,' + itemPackageImg.itemPackageImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemImageName);
  }

  // getImageSrc(itemImg) {
  //   let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
  //   return this.sanitizer.bypassSecurityTrustUrl(imageData);
  // }

  pondHandleAddFile(event) {
    this.packageItem.itemPackage.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.packageItem.itemPackage.itemImgsRaw.length; i++) {
      // console.log(this.itemPackage.itemPkgImgs[i].name + ' ' + event.file.file.name)
      if (this.packageItem.itemPackage.itemImgsRaw[i].name === event.file.file.name) {
        this.packageItem.itemPackage.itemImgsRaw.splice(i, 1);
      }
    }
  }

}
