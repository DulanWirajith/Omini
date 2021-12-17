import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {ItemService} from "../../../../_service/item.service";
import {environment} from "../../../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";

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
  itemImg;
  imgUrl;
  isNewFeature = false;
  isNewItem = false;
  businessProfileCategory;
  items = [];
  @ViewChild('baManageFormItem', {static: true}) public baManageFormItem: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;
  image

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private sanitizer: DomSanitizer) {
    this.item = this.itemService.getNewItem();
  }

  ngOnInit(): void {
    this.getBusinessCategories();
    // this.getItemsOrdered();
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  getItemFeatures() {
    this.itemService.getItemFeatures(this.item.businessProfileCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
    })
  }

  onSubmit() {
    // //console.log(this.item.itemItemFeatures)
    // let itemFeatures = [];
    // for (let itemFeature of this.item.itemItemFeatures) {
    //   itemFeatures.push({
    //     itemFeature: itemFeature
    //   })
    // }
    // this.item.itemItemFeatures = itemFeatures;

    this.item.businessProfileCategory = {
      businessProfile: {
        businessProId: "B321"
      },
      businessCategory: this.item.businessProfileCategory
    };

    // //console.log(this.item)
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.itemImg, this.itemImg.name);
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.itemService.addItem(uploadImageData).subscribe((reply) => {
      this.baManageFormItem.resetForm(this.itemService.getNewItem());
      this.item.itemItemFeatures = [];
    })
  }

  getItemsOrdered() {
    this.itemService.getItemsOrdered("B321", this.businessProfileCategory.businessCategoryId, 0, 100).subscribe((items) => {
      this.items = items;
      // this.getImageSrc(items[0])
      console.log(items)
    })
  }

  addFeature() {
    if (this.itemFeature !== undefined) {
      this.item.itemItemFeatures.push({
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
        name: this.newItemFeature,
        businessCategory: this.item.businessProfileCategory
      }
    );
    this.newItemFeature = '';
    this.baManageFormItemFeature.resetForm()
  }

  addNewItemFeature() {
    for (let itemFeature of this.newItemFeaturesTemp) {
      this.item.itemItemFeatures.push({
        itemFeature: itemFeature
      })
    }
    this.isNewFeature = false;
    this.newItemFeaturesTemp = [];
  }

  processFile(event) {
    this.itemImg = event.target.files[0];
  }

  getImageSrc(item) {
    // let image;
    // const reader = new FileReader();
    // reader.onload = (e) => this.image = e.target.result;
    // reader.readAsDataURL(new Blob([item.itemImg]));
    let imageData = 'data:' + item.itemImgType + ';base64,' + item.itemImg;
    return this.sanitizer.bypassSecurityTrustUrl(imageData);
    // return image;
    // return environment.backend_url+'item/itemImg/'+item.itemId
  }
}
