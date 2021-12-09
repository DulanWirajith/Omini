import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../../_service/business-register.service";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-ba-manage-package',
  templateUrl: './ba-manage-package.component.html',
  styleUrls: ['./ba-manage-package.component.css']
})
export class BaManagePackageComponent implements OnInit {

  package;
  items;
  item;
  packageImg;
  imgUrl;
  itemsAdded;
  businessCategories = [];
  isNewPackage = false;
  @ViewChild('baManageFormPackage', {static: true}) public baManageFormPackage: NgForm;
  @ViewChild('baManageFormPackageItemExs', {static: true}) public baManageFormPackageItemExs: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService, private businessAccountService: BusinessAccountService) {
    this.package = this.getNewPackage();
  }

  ngOnInit(): void {
    // this.getItems();
    this.getBusinessCategories();
    console.log(9)
  }

  getBusinessCategories() {
    this.businessRegisterService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  addItem() {
    if (this.item !== undefined) {
      this.package.itemItemPackages.push({
        item: this.item
      })
      this.baManageFormPackageItemExs.resetForm()
      this.item = undefined;
    }
  }

  onSubmit() {
    // let packageItems = [];
    // for (let packageItem of this.package.itemItemPackages) {
    //   packageItems.push({
    //     item: packageItem
    //   })
    // }
    // this.package.itemItemPackages = packageItems;

    this.package.businessProfileCategory = {
      businessProfile: {
        businessProId: "B321"
      },
      businessCategory: this.package.businessProfileCategory
    };

    console.log(this.package)
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.packageImg, this.packageImg.name);
    uploadImageData.append('package', new Blob([JSON.stringify(this.package)],
      {
        type: "application/json"
      }));
    this.businessAccountService.addPackage(uploadImageData).subscribe((reply) => {
      this.baManageFormPackage.resetForm(this.getNewPackage());
      this.package.itemItemPackages = [];
      this.item = undefined;
    })
  }

  processFile(event) {
    this.packageImg = event.target.files[0];
  }

  getItems() {
    this.businessAccountService.getItemsBusinessCategory("B321", this.package.businessProfileCategory.businessCategoryId).subscribe((items) => {
      this.items = items;
    })
  }

  getNewPackage() {
    return {
      name: "",
      description: "",
      price: "",
      discount: "",
      discountType: "None",
      businessProfileCategory: undefined,
      itemItemPackages: []
    }
  }
}
