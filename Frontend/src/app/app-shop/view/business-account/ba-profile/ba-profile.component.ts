import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessAccountService} from "../../../_service/business-account.service";
import * as $ from "jquery";
import {environment} from "../../../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import {ShopCartService} from "../../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../../_service/login.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-ba-profile',
  templateUrl: './ba-profile.component.html',
  styleUrls: ['./ba-profile.component.css']
})
export class BaProfileComponent implements OnInit {

  businessProfile;
  businessCategories = [];
  businessAreas = [];
  passwordView = {
    curView: false,
    newView: false,
    cView: false,
    toggleView: false
  };
  countries;
  regions;
  towns;
  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    // labelIdle: '<div class="btn btn-primary mt-3 mb-3"><i class="fi-cloud-upload me-1"></i>Upload photos</div></br>or drag them in',
    labelIdle:
      '<div class="btn" style="margin-left: 0px;width: 100%">' +
      '<i class="fi-camera-plus" style="font-size: 30px;margin-right: 20px"></i>Upload image</div></br>or drag them in',
    acceptedFileTypes: 'image/jpeg, image/png'
  };
  @ViewChild('imageInput') imageInput: any;
  user;

  constructor(private businessAccountService: BusinessAccountService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    this.businessProfile = businessAccountService.getNewBusinessProfile();
  }

  ngOnInit(): void {
    this.togglePasswordBtn();
    this.getBusinessProfile();
    this.getCountries();
    this.getTownsWIthDistrict();
    this.getBusinessCategories();
  }

  getBusinessProfile() {
    this.businessAccountService.getBusinessProfile(this.loginService.getUser().userId, false, '0', 'B').subscribe((businessProfile) => {
      // this.user = this.loginService.getUser();
      // let businessProfile = this.loginService.getUser();
      //console.log(businessProfile)
      if (businessProfile !== null) {
        businessProfile.dbayUser.password = '';
        businessProfile.dbayUser.passwordC = '';
        businessProfile.dbayUser.cPassword = '';
        businessProfile.dbayUser.dbayUserImgsRaw = [];
        // console.log(businessProfile)
        this.businessProfile = businessProfile;
        this.getDistricts(businessProfile.town.region.country.countryId)
        this.getTowns(businessProfile.town.region.regionId)
      }
    })
  }

  getCountries() {
    this.businessAccountService.getCountries().subscribe((countries) => {
      this.countries = countries;
    })
  }

  getDistricts(countryId) {
    this.businessAccountService.getDistricts(countryId).subscribe((regions) => {
      this.regions = regions;
    })
  }

  getTowns(regionId) {
    this.businessAccountService.getTowns(regionId).subscribe((towns) => {
      this.towns = towns;
    })
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      // for (let i = 0; i < businessCategories.length; i++) {
      //   businessCategories[i] = {
      //     businessCategory: businessCategories[i]
      //   }
      // }
      this.businessCategories = businessCategories;
    })
  }

  getTownsWIthDistrict() {
    this.businessAccountService.getTownsWIthDistrict().subscribe((businessAreas) => {
      // for (let i = 0; i < businessAreas.length; i++) {
      //   businessAreas[i] = {
      //     town: businessAreas[i]
      //   }
      // }
      this.businessAreas = businessAreas;
    })
  }

  notSpecifiedSocials() {
    return this.businessProfile.socialFb === '' && this.businessProfile.socialInsta === '' && this.businessProfile.socialTwitter === '' && this.businessProfile.socialLinkedIn === '';
  }

  togglePasswordBtn() {
    let that = this;
    $('#password-toggle-indicator1').click(function () {
      that.passwordView.curView ? that.passwordView.curView = false : that.passwordView.curView = true
    })
    $('#password-toggle-indicator2').click(function () {
      that.passwordView.newView ? that.passwordView.newView = false : that.passwordView.newView = true
    })
    $('#password-toggle-indicator3').click(function () {
      that.passwordView.cView ? that.passwordView.cView = false : that.passwordView.cView = true
    })
    // $('#password-collapse').on('classChanged', function () {
    //   //console.log(456)
    //   if ($("#password-collapse").hasClass("show")) {
    //     that.passwordView.toggleView = false
    //   } else {
    //     that.passwordView.toggleView = true
    //   }
    // });
    // (click)="passwordView.curView?passwordView.curView=false:passwordView.curView=true"
  }

  onSubmit() {
    this.setDialogBox('Do you want update your profile?');
    this.confirmationSub.observers = [];
    this.confirmationSub.subscribe(() => {
      let businessTypes = [];
      let businessAreas = [];
      for (let businessType of this.businessProfile.businessProfileCategories) {
        if (businessType.businessCategory === undefined) {
          businessTypes.push({
            name: businessType.name,
            businessCategory: businessType,
            businessProfile: {
              businessProId: this.businessProfile.businessProId
            }
          })
        }
      }
      if (businessTypes.length > 0) {
        this.businessProfile.businessProfileCategories = businessTypes;
      }

      for (let businessArea of this.businessProfile.businessAreas) {
        if (businessArea.town === undefined) {
          businessAreas.push({
            name: businessArea.name,
            town: businessArea,
            businessProfile: {
              businessProId: this.businessProfile.businessProId
            }
          })
        }
      }
      if (businessAreas.length > 0) {
        this.businessProfile.businessAreas = businessAreas;
      }
      //console.log(this.businessProfile)
      const uploadImageData = new FormData();
      // for (let dbayUserImg of this.businessProfile.dbayUser.dbayUserImgsRaw) {
      //   uploadImageData.append('imageFile', dbayUserImg, dbayUserImg.name);
      if (this.businessProfile.dbayUser.dbayUserImgRaw !== undefined) {
        uploadImageData.append('imageFile', this.businessProfile.dbayUser.dbayUserImgRaw, this.businessProfile.dbayUser.dbayUserImgRaw.name);
      }

      // }
      uploadImageData.append('businessProfile', new Blob([JSON.stringify(this.businessProfile)],
        {
          type: "application/json"
        }));
      this.businessAccountService.updateBusinessProfile(uploadImageData, this.businessProfile.businessProId).subscribe((businessProfile) => {
        //console.log(businessProfile)
        this.businessProfile.dbayUser.password = '';
        this.businessProfile.dbayUser.passwordC = '';
        this.businessProfile.dbayUser.cPassword = '';
        this.businessProfile.dbayUser.dbayUserImgRaw = undefined;
        this.businessProfile.dbayUser.userImgName = businessProfile.dbayUser.userImgName;
        this.businessProfile.dbayUser.userImgType = businessProfile.dbayUser.userImgType;
        // if (businessProfile.dbayUser.dbayUserImgs.length > 0) {
        //   this.businessProfile.dbayUser.dbayUserImgs = businessProfile.dbayUser.dbayUserImgs;
        // }
        // localStorage.setItem('user', JSON.stringify(this.user));
        this.businessAccountService.navBarSub.next({
          name: 'Business',
          value: this.businessProfile.defaultBusiness
        })
        this.loginService.getUser().businessProfile.defaultBusiness = this.businessProfile.defaultBusiness;
        localStorage.setItem('user', JSON.stringify(this.loginService.getUser()))
        // this.businessProfile = businessProfile;
        this.imageInput.removeFiles();
        this.setDialogBox('Your profile has been updated', true)
        // this.businessProfile.dbayUser.dbayUserImgs = this.businessProfile.dbayUser.dbayUserImgs.concat(businessProfile.dbayUser.dbayUserImgs);
      })
    })
  }

  setDefaultBusiness(businessProfile) {
    if (businessProfile.businessProfileCategories.length === 1) {
      businessProfile.defaultBusiness = businessProfile.businessProfileCategories[0].name;
    }
  }

  pondHandleAddFile(event) {
    this.businessProfile.dbayUser.dbayUserImgRaw = event.file.file;
  }

  pondHandlerRemoveFile(event) {
    // for (let i = 0; i < this.businessProfile.dbayUser.dbayUserImgsRaw.length; i++) {
    //   if (this.businessProfile.dbayUser.dbayUserImgsRaw[i].name === event.file.file.name) {
    //     this.businessProfile.dbayUser.dbayUserImgsRaw.splice(i, 1);
    //   }
    // }
    this.businessProfile.dbayUser.dbayUserImgRaw = undefined;
  }

  getImageSrc(userImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + userImg.userImgName);
  }

  confirmation = {
    reply: false,
    message: ''
  };

  confirmationSub = new Subject();

  setDialogBox(message, reply = false) {
    this.confirmation.reply = reply;
    this.confirmation.message = message;
    // console.log(this.confirmation.reply)
  }
}
