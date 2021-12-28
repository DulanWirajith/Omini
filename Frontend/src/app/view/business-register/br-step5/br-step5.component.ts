import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-br-step5',
  templateUrl: './br-step5.component.html',
  styleUrls: ['./br-step5.component.css']
})
export class BrStep5Component implements OnInit {

  businessProfile;

  @ViewChild('brForm5', {static: true}) public brForm5: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
      this.businessProfile.user.verificationCode = '';
    }
  }

  onSubmit() {
    let businessTypes = [];
    let businessAreas = [];
    for (let businessType of this.businessProfile.businessProfileCategories) {
      businessTypes.push({
        businessCategory: businessType,
        businessProfile: {
          businessProId: this.businessProfile.businessProId
        }
      })
    }
    this.businessProfile.businessProfileCategories = businessTypes;

    for (let businessArea of this.businessProfile.businessAreas) {
      businessAreas.push({
        town: businessArea,
        businessProfile: {
          businessProId: this.businessProfile.businessProId
        }
      })
    }
    this.businessProfile.businessAreas = businessAreas;
    console.log(this.businessProfile)
    this.businessAccountService.addBusinessProfile(this.businessProfile).subscribe((reply) => {
      localStorage.removeItem('br');
      this.businessAccountService.step.next(6)
    })
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(4);
  }
}
