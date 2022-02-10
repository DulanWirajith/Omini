import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {ItemService} from "../../../../_service/item.service";
import {environment} from "../../../../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import * as $ from "jquery";
import {ShopCartService} from "../../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../../_service/login.service";


@Component({
  selector: 'app-ba-manage-item',
  templateUrl: './ba-manage-item.component.html',
  styleUrls: ['./ba-manage-item.component.css']
})
export class BaManageItemComponent implements OnInit {

  item;
  newItemFeature;
  newItemFeaturesTemp = [];
  itemPackageFeature;
  businessCategories = [];
  itemFeatures = [];
  isNewFeature = false;
  businessProfileCategory;
  items = [];
  @ViewChild('baManageFormItem', {static: true}) public baManageFormItem: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  @ViewChild('imageInput') imageInput: any;
  @ViewChild('imageInputE') imageInputE: any;

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    this.item = this.itemService.getNewItem();
    // this.businessAccountService.businessCategoriesSub.observers = [];
    this.businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    });
    // console.log(4)
    // this.businessAccountService.businessCategorySub.observers = [];
    this.businessAccountService.businessCategorySub.subscribe((businessCategoryId) => {
      this.getItemsOrdered(businessCategoryId);
      // console.log(2)
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemsOrdered();
    // console.log(3)
    this.businessCategories = this.businessAccountService.businessCategories;
    if (this.businessAccountService.businessCategory !== undefined) {
      this.getItemsOrdered(this.businessAccountService.businessCategory.businessCategoryId);
    }
  }

  // getBusinessCategories() {
  //   console.log(this.businessCategories)
  // }

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
      this.items.push(item);
      this.baManageFormItem.resetForm(this.itemService.getNewItem());
      this.item.itemPackage.itemPackageItemPackageFeatures = [];
      this.imageInput.removeFiles();
      if (document.getElementById('btnAddItem') !== null) {
        document.getElementById('btnAddItem').click()
      }
      this.item.isNewItem = false;
    })
  }

  getItemsOrdered(businessCategoryId) {
    this.itemService.getItemsOrdered(this.loginService.getUser().userId, businessCategoryId, 0, 100).subscribe((items) => {
      this.items = items;
      for (let item of this.items) {
        item.itemPackage.itemImgsRaw = [];
        item.itemPackage.businessProfileCategory = {
          businessProfile: undefined,
          businessCategory: undefined
        }
      }
      //console.log(this.items)
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


// processFile(event, val) {
//   console.log(event.target.files)
//   // this.itemImg.push(event.target.files[0]);
//   // this.itemImg.push(event.target.files[1]);
//   if (event.target.files && event.target.files[0]) {
//     let filesAmount = event.target.files.length;
//     for (let i = 0; i < filesAmount; i++) {
//       if (val === 'n') {
//         this.itemImgs.push(event.target.files[i]);
//         let reader = new FileReader();
//
//         reader.onload = (event: any) => {
//           // console.log(event.target.result);
//           this.images.push(event.target.result);
//
//         }
//
//         reader.readAsDataURL(event.target.files[i]);
//       } else if (val === 'e') {
//         this.itemImgsE.push(event.target.files[i]);
//         let reader = new FileReader();
//
//         reader.onload = (event: any) => {
//           // console.log(event.target.result);
//           this.imagesE.push(event.target.result);
//
//         }
//
//         reader.readAsDataURL(event.target.files[i]);
//       }
//     }
//   }
// }
