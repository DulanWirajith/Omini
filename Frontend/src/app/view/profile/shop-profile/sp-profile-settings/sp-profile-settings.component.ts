import {Component, OnInit} from '@angular/core';
import {BusinessRegisterService} from "../../../../_service/business-register.service";
import {BusinessAccountService} from "../../../../_service/business-account.service";
import * as $ from 'jquery';

@Component({
  selector: 'app-sp-profile-settings',
  templateUrl: './sp-profile-settings.component.html',
  styleUrls: ['./sp-profile-settings.component.css']
})
export class SpProfileSettingsComponent implements OnInit {

  businessProfile;
  businessCategories = [];
  businessAreas = [];
  passwordView = {
    curView: false,
    newView: false,
    cView: false,
    toggleView: false
  };

  constructor(private businessRegisterService: BusinessRegisterService, private businessAccountService: BusinessAccountService) {
    this.businessProfile = businessRegisterService.getNewBR();
  }

  ngOnInit(): void {
    this.togglePasswordBtn();
    this.getBusinessProfile();
    this.getTownsWIthDistrict();
    this.getBusinessCategories();
  }

  getBusinessProfile() {
    this.businessAccountService.getBusinessProfile("B321").subscribe((businessProfile) => {
      businessProfile.user.password = '';
      businessProfile.user.passwordC = '';
      businessProfile.user.cPassword = '';
      // console.log(businessProfile)
      this.businessProfile = businessProfile;
    })
  }

  getBusinessCategories() {
    this.businessRegisterService.getBusinessCategories().subscribe((businessCategories) => {
      // for (let i = 0; i < businessCategories.length; i++) {
      //   businessCategories[i] = {
      //     businessCategory: businessCategories[i]
      //   }
      // }
      this.businessCategories = businessCategories;
    })
  }

  getTownsWIthDistrict() {
    this.businessRegisterService.getTownsWIthDistrict().subscribe((businessAreas) => {
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
    //   console.log(456)
    //   if ($("#password-collapse").hasClass("show")) {
    //     that.passwordView.toggleView = false
    //   } else {
    //     that.passwordView.toggleView = true
    //   }
    // });
    // (click)="passwordView.curView?passwordView.curView=false:passwordView.curView=true"
  }

  onSubmit() {
    let businessTypes = [];
    let businessAreas = [];
    for (let businessType of this.businessProfile.businessProfileCategories) {
      businessTypes.push({
        businessCategory: businessType
      })
    }
    this.businessProfile.businessProfileCategories = businessTypes;

    for (let businessArea of this.businessProfile.businessAreas) {
      businessAreas.push({
        town: businessArea
      })
    }
    this.businessProfile.businessAreas = businessAreas;
    console.log(this.businessProfile)
    this.businessAccountService.updateBusinessProfile(this.businessProfile).subscribe((businessProfile) => {
      console.log(businessProfile)
      this.businessProfile = businessProfile;
    })
  }
}
