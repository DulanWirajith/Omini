import {Component, OnInit, ViewChild} from '@angular/core';
import {CustomerAccountService} from "../../../_service/customer-account.service";
import {environment} from "../../../../../environments/environment";
import {BusinessAccountService} from "../../../../app-shop/_service/business-account.service";
import {DomSanitizer} from "@angular/platform-browser";
import {LoginService} from "../../../../_service/login.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-c-profile',
  templateUrl: './c-profile.component.html',
  styleUrls: ['./c-profile.component.css']
})
export class CProfileComponent implements OnInit {

  customerProfile;
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

  constructor(private customerAccountService: CustomerAccountService, private sanitizer: DomSanitizer, private loginService: LoginService) {
    this.customerProfile = customerAccountService.getNewCustomerProfile();
  }

  ngOnInit(): void {
    this.togglePasswordBtn();
    this.getCustomerProfile();
    this.getCountries();
    // this.getTownsWIthDistrict();
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

  getCustomerProfile() {
    this.customerAccountService.getCustomerProfile(this.loginService.getUser().userId).subscribe((customerProfile) => {
      //console.log(customerProfile)
      if (customerProfile !== null) {
        customerProfile.dbayUser.password = '';
        customerProfile.dbayUser.passwordC = '';
        customerProfile.dbayUser.cPassword = '';
        customerProfile.dbayUser.dbayUserImgsRaw = [];
        this.customerProfile = customerProfile;
        this.imageInput.removeFiles();
      }
    })
  }

  onSubmit() {
    //console.log(this.customerProfile)
    this.setDialogBox('Do you want update your profile?');
    this.confirmationSub.observers = [];
    this.confirmationSub.subscribe(() => {
      const uploadImageData = new FormData();
      // for (let dbayUserImg of this.customerProfile.dbayUser.dbayUserImgsRaw) {
      if (this.customerProfile.dbayUser.dbayUserImgRaw !== undefined) {
        uploadImageData.append('imageFile', this.customerProfile.dbayUser.dbayUserImgRaw, this.customerProfile.dbayUser.dbayUserImgRaw.name);
      }
      // }
      uploadImageData.append('customerProfile', new Blob([JSON.stringify(this.customerProfile)],
        {
          type: "application/json"
        }));
      this.customerAccountService.updateCustomerProfile(uploadImageData, this.customerProfile.customerProId).subscribe((customerProfile) => {
        //console.log(customerProfile)
        this.customerProfile.dbayUser.password = '';
        this.customerProfile.dbayUser.passwordC = '';
        this.customerProfile.dbayUser.cPassword = '';
        this.customerProfile.dbayUser.dbayUserImgRaw = undefined;
        this.customerProfile.dbayUser.userImgName = customerProfile.dbayUser.userImgName;
        this.customerProfile.dbayUser.userImgType = customerProfile.dbayUser.userImgType;
        // customerProfile.dbayUser.dbayUserImgs = [];
        // this.customerProfile = customerProfile;
        this.imageInput.removeFiles();
        this.setDialogBox('Your profile has been updated', true)
        // this.customerProfile.dbayUser.dbayUserImgs = this.customerProfile.dbayUser.dbayUserImgs.concat(customerProfile.dbayUser.dbayUserImgs);
      })
    })
  }

  getCountries() {
    this.customerAccountService.getCountries().subscribe((countries) => {
      this.countries = countries;
    })
  }

  getDistricts(countryId) {
    this.customerAccountService.getDistricts(countryId).subscribe((regions) => {
      this.regions = regions;
    })
  }

  getTowns(regionId) {
    this.customerAccountService.getTowns(regionId).subscribe((towns) => {
      this.towns = towns;
    })
  }

  pondHandleAddFile(event) {
    this.customerProfile.dbayUser.dbayUserImgRaw = event.file.file;
  }

  pondHandlerRemoveFile(event) {
    // for (let i = 0; i < this.customerProfile.dbayUser.dbayUserImgsRaw.length; i++) {
    //   if (this.customerProfile.dbayUser.dbayUserImgsRaw[i].name === event.file.file.name) {
    //     this.customerProfile.dbayUser.dbayUserImgsRaw.splice(i, 1);
    //   }
    // }
    this.customerProfile.dbayUser.dbayUserImgRaw = undefined;
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
