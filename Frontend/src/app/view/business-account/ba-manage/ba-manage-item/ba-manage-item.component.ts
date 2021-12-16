import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../../_service/business-register.service";
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../_service/business-account.service";

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
  @ViewChild('baManageFormItem', {static: true}) public baManageFormItem: NgForm;
  @ViewChild('baManageFormItemFeature', {static: true}) public baManageFormItemFeature: NgForm;
  @ViewChild('baManageFormItemFeatureExs', {static: true}) public baManageFormItemFeatureExs: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.item = this.getNewItem();
  }

  ngOnInit(): void {
    this.getBusinessCategories();
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  getItemFeatures() {
    this.businessAccountService.getItemFeatures(this.item.businessProfileCategory.businessCategoryId).subscribe((itemFeatures) => {
      this.itemFeatures = itemFeatures;
    })
  }

  onSubmit() {
    // console.log(this.item.itemItemFeatures)
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

    // console.log(this.item)
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.itemImg, this.itemImg.name);
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.businessAccountService.addItem(uploadImageData).subscribe((reply) => {
      this.baManageFormItem.resetForm(this.getNewItem());
      this.item.itemItemFeatures = [];
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

  getNewItem() {
    return {
      itemTitle: "",
      itemQty: 1,
      itemDiscount: "",
      itemDiscountType: "None",
      itemPrice: "",
      itemDescription: "",
      businessProfileCategory: undefined,
      itemItemFeatures: []
    }
  }
}
