import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {ItemService} from "../../../../_service/item.service";
import {environment} from "../../../../../../environments/environment";
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

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.item = this.itemService.getNewItem();
    businessAccountService.businessCategoriesSub.subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    });

  }

  ngOnInit(): void {
    // this.getBusinessCategories();
    // this.getItemsOrdered();
    this.businessCategories = this.businessAccountService.businessCategories;
  }

  // getBusinessCategories() {
  //   console.log(this.businessCategories)
  // }

  getItemFeatures() {
    this.itemService.getItemFeatures(this.item.businessProfileCategory.businessCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
      this.item.itemItemFeatures = [];
    })
  }

  onSubmit() {
    this.item.businessProfileCategory.businessProfile = {
      businessProId: "B321"
    };

    console.log(this.item)
    const uploadImageData = new FormData();
    for (let itemImg of this.item.itemImgsRaw) {
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
        item.itemImgsRaw = [];
        item.businessProfileCategory = {
          businessProfile: undefined,
          businessCategory: undefined
        }
      }
      console.log(this.items)
    })
  }

  addFeature() {
    if (this.itemFeature !== undefined) {
      this.item.itemItemFeatures.push({
        item: {itemId: this.item.itemId},
        itemFeature: this.itemFeature
      })
      this.baManageFormItemFeatureExs.resetForm()
      this.itemFeature = undefined;
    }
  }

  addFeatureTemp() {
      this.newItemFeaturesTemp.push(
        {
          itemFeatureId: 0,
          name: this.newItemFeature
        }
      );
      this.newItemFeature = '';
      this.baManageFormItemFeature.resetForm()
  }

  addNewItemFeature() {
      for (let itemFeature of this.newItemFeaturesTemp) {
        this.item.itemItemFeatures.push({
          item: {itemId: this.item.itemId},
          itemFeature: itemFeature
        })
      }
      this.isNewFeature = false;
      this.newItemFeaturesTemp = [];
  }

  // @ViewChild('baManageFormItemE', {static: true}) public baManageFormItemE: NgForm;

  pondHandleAddFile(event) {
    this.item.itemImgsRaw.push(event.file.file);
  }

  pondHandlerRemoveFile(event) {
    for (let i = 0; i < this.item.itemImgsRaw.length; i++) {
      // console.log(this.itemImgs[i].name + ' ' + event.file.file.name)
      if (this.item.itemImgsRaw[i].name === event.file.file.name) {
        this.item.itemImgsRaw.splice(i, 1);
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
