import {Component, OnInit} from '@angular/core';
import * as $ from "jquery";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-cp-profile-settings',
  templateUrl: './cp-profile-settings.component.html',
  styleUrls: ['./cp-profile-settings.component.css']
})
export class CpProfileSettingsComponent implements OnInit {

  customerProfile;
  passwordView = {
    curView: false,
    newView: false,
    cView: false,
    toggleView: false
  };

  constructor(private customerAccountService: CustomerAccountService) {
    this.customerProfile = customerAccountService.getNewCustomerProfile();
  }

  ngOnInit(): void {
    this.togglePasswordBtn();
    this.getCustomerProfile();
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
    this.customerAccountService.getCustomerProfile("B321").subscribe((customerProfile) => {
      if (customerProfile !== null) {
        customerProfile.user.password = '';
        customerProfile.user.passwordC = '';
        customerProfile.user.cPassword = '';
        //console.log(customerProfile)
        this.customerProfile = customerProfile;
      }
    })
  }

  onSubmit() {

  }
}
