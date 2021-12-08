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
  @ViewChild('baManageForm', {static: true}) public baManageForm: NgForm;
  @ViewChild('baManageFormFeature', {static: true}) public baManageFormFeature: NgForm;
  @ViewChild('baManageFormFeatureExs', {static: true}) public baManageFormFeatureExs: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService, private businessAccountService: BusinessAccountService) {
    this.item = this.getNewItem();
  }

  ngOnInit(): void {
    this.getBusinessCategories();
  }

  getBusinessCategories() {
    this.businessRegisterService.getBusinessCategories().subscribe((businessCategories) => {
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
    let itemFeatures = [];
    for (let itemFeature of this.item.itemItemFeatures) {
      itemFeatures.push({
        itemFeature: itemFeature
      })
    }
    this.item.itemItemFeatures = itemFeatures;

    this.item.businessProfileCategory = {
      businessProfile: {
        businessProId: "B321"
      },
      businessCategory: this.item.businessProfileCategory
    };

    console.log(this.item)
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.itemImg, this.itemImg.name);
    uploadImageData.append('item', new Blob([JSON.stringify(this.item)],
      {
        type: "application/json"
      }));
    this.businessAccountService.addItem(uploadImageData).subscribe((reply) => {
      this.baManageForm.resetForm(this.getNewItem());
      this.item.itemItemFeatures = [];
    })
  }

  addFeature() {
    if (this.itemFeature !== undefined) {
      this.item.itemItemFeatures.push(this.itemFeature)
      this.baManageFormFeatureExs.resetForm()
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
    this.baManageFormFeature.resetForm()
  }

  addNewItemFeature() {
    for (let itemFeature of this.newItemFeaturesTemp) {
      this.item.itemItemFeatures.push(itemFeature)
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
      itemDiscountType: "",
      itemPrice: "",
      itemDescription: "",
      businessProfileCategory: undefined,
      itemItemFeatures: []
    }
  }
}
