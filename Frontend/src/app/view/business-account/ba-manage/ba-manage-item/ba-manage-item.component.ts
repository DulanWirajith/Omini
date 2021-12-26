import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {ItemService} from "../../../../_service/item.service";
import {environment} from "../../../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import * as $ from "jquery";

@Component({
  selector: 'app-ba-manage-item',
  templateUrl: './ba-manage-item.component.html',
  styleUrls: ['./ba-manage-item.component.css']
})
export class BaManageItemComponent implements OnInit {

  item;
  newItemFeature;
  newItemFeaturesTemp = [];
  itemFeature;
  businessCategories = [];
  itemFeatures = [];
  itemImgs = [];
  isNewFeature = false;
  isNewItem = false;
  businessProfileCategory;
  items = [];
  @ViewChild('baManageFormItem', {static: true}) public baManageFormItem: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  image;
  images = [];
  @ViewChild('imageInput') imageInput: any;
  @ViewChild('imageInputE') imageInputE: any;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.item = this.itemService.getNewItem();
    this.itemE = this.itemService.getNewItem();
    businessAccountService.businessCategories.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemsOrdered();
  }

  // getBusinessCategories() {
  //   console.log(this.businessCategories)
  // }

  getItemFeatures(val) {
    if (val === 'n') {
      this.itemService.getItemFeatures(this.item.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
        this.itemFeatures = itemFeatures;
        this.item.itemItemFeatures = [];
      })
    } else if (val === 'e') {
      this.itemService.getItemFeatures(this.itemE.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
        this.itemFeaturesE = itemFeatures;
        this.itemE.itemItemFeatures = [];
      })
    }
  }

  onSubmit() {
    this.item.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    console.log(this.itemImgs)
    const uploadImageData = new FormData();
    for (let itemImg of this.itemImgs) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.itemService.addItem(uploadImageData).subscribe((item) => {
      this.items.push(item)
      this.baManageFormItem.resetForm(this.itemService.getNewItem());
      this.item.itemItemFeatures = [];
      this.imageInput.removeFiles();
    })
  }

  getItemsOrdered() {
    this.itemService.getItemsOrdered("B321", this.businessProfileCategory.businessCategoryId, 0, 100).subscribe((items) => {
      this.items = items;
      for (let item of this.items) {
        item.itemItemFeatures = [];
      }
    })
  }

  addFeature(val) {
    if (val === 'n') {
      if (this.itemFeature !== undefined) {
        this.item.itemItemFeatures.push({
          item: {itemId: this.item.itemId},
          itemFeature: this.itemFeature
        })
        this.baManageFormItemFeatureExs.resetForm()
        this.itemFeature = undefined;
      }
    } else if (val === 'e') {
      if (this.itemFeatureE !== undefined) {
        this.itemE.itemItemFeatures.push({
          item: {itemId: this.itemE.itemId},
          itemFeature: this.itemFeatureE
        })
        this.baManageFormItemFeatureExsE.resetForm()
        this.itemFeatureE = undefined;
      }
    }
  }

  addFeatureTemp(val) {
    if (val === 'n') {
      this.newItemFeaturesTemp.push(
        {
          itemFeatureId: 0,
          name: this.newItemFeature,
          businessCategory: this.item.businessProfileCategory.businessCategory
        }
      );
      this.newItemFeature = '';
      this.baManageFormItemFeature.resetForm()
    } else if (val === 'e') {
      this.newItemFeaturesTempE.push(
        {
          itemFeatureId: 0,
          name: this.newItemFeatureE,
          businessCategory: this.itemE.businessProfileCategory.businessCategory
        }
      );
      this.newItemFeatureE = '';
      this.baManageFormItemFeatureE.resetForm()
    }
  }

  addNewItemFeature(val) {
    if (val === 'n') {
      for (let itemFeature of this.newItemFeaturesTemp) {
        this.item.itemItemFeatures.push({
          item: {itemId: this.item.itemId},
          itemFeature: itemFeature
        })
      }
      this.isNewFeature = false;
      this.newItemFeaturesTemp = [];
    } else if (val === 'e') {
      for (let itemFeature of this.newItemFeaturesTempE) {
        this.itemE.itemItemFeatures.push({
          item: {itemId: this.itemE.itemId},
          itemFeature: itemFeature
        })
      }
      this.isNewFeatureE = false;
      this.newItemFeaturesTempE = [];
    }
  }

  getItemSelected(item) {
    let index: any = this.items.findIndex(itemObj => {
      return itemObj.itemId === item.itemId
    })
    // console.log(this.items[index])
    if (this.items[index].itemItemFeatures.length === 0) {
      this.itemService.getItemSelected(item.itemId).subscribe((item) => {
        // Object.assign(this.items[index], item)
        this.items[index] = item;
        this.itemFeaturesE = item.itemFeatures;
        this.itemE = this.items[index]
        console.log(this.items[index])
      })
    } else {
      this.itemE = this.items[index]
    }
  }

  setItemAvailable(item) {
    this.itemService.setItemAvailable(item.itemId).subscribe((reply) => {
      item.itemAvailable = reply;
    })
  }

  getImageSrc(itemImg) {
    let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    return this.sanitizer.bypassSecurityTrustUrl(imageData);
  }

  //===============================================Edit=================================================================

  itemE;
  itemFeaturesE = [];
  itemFeatureE;
  isNewItemE;
  isNewFeatureE;
  newItemFeatureE;
  newItemFeaturesTempE = [];
  itemImgsE = [];
  @ViewChild('baManageFormItemE', {static: true}) public baManageFormItemE: NgForm;
  @ViewChild('baManageFormItemFeatureE', {static: true}) public baManageFormItemFeatureE: NgForm;
  @ViewChild('baManageFormItemFeatureExsE', {static: true}) public baManageFormItemFeatureExsE: NgForm;

  onSubmitE() {
    this.itemE.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    const uploadImageData = new FormData();
    for (let itemImg of this.itemImgsE) {
      uploadImageData.append('imageFile', itemImg, itemImg.name);
    }
    uploadImageData.append('item', new Blob([JSON.stringify(this.itemE)],
      {
        type: "application/json"
      }));
    this.itemService.updateItem(uploadImageData, this.itemE.itemId).subscribe((itemE) => {
      this.imageInputE.removeFiles();
      this.itemE.itemImgs = this.itemE.itemImgs.concat(itemE.itemImgs);
    })
  }

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  }

  pondHandleAddFile(event, val) {
    if (val === 'n') {
      this.itemImgs.push(event.file.file);
    } else if (val === 'e') {
      this.itemImgsE.push(event.file.file);
    }
  }

  pondHandlerRemoveFile(event, val) {
    if (val === 'n') {
      for (let i = 0; i < this.itemImgs.length; i++) {
        console.log(this.itemImgs[i].name + ' ' + event.file.file.name)
        if (this.itemImgs[i].name === event.file.file.name) {
          this.itemImgs.splice(i, 1);
        }
      }
    } else if (val === 'e') {
      for (let i = 0; i < this.itemImgsE.length; i++) {
        if (this.itemImgsE[i].name === event.file.file.name) {
          this.itemImgsE.splice(i, 1);
        }
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
