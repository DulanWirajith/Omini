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
  verification;

  @ViewChild('brForm5', {static: true}) public brForm5: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
      this.businessProfile.dbayUser.verificationCode = '';
      this.sendVerification();
    }
  }

  sendVerification() {
    this.verification = '';
    this.businessAccountService.sendVerification().subscribe((user) => {
      this.verification = user.verificationCode;
    })
  }

  onSubmit() {
    // let businessTypes = [];
    // let businessAreas = [];
    for (let i = 0; i < this.businessProfile.businessProfileCategories.length; i++) {
      if (this.businessProfile.businessProfileCategories[i].businessProfile === undefined) {
        // console.log(456)
        this.businessProfile.businessProfileCategories[i] = {
          name: this.businessProfile.businessProfileCategories[i].name,
          businessCategory: this.businessProfile.businessProfileCategories[i],
          businessProfile: {
            businessProId: this.businessProfile.businessProId
          }
        }
      }
    }
    // this.businessProfile.businessProfileCategories = businessTypes;

    for (let i = 0; i < this.businessProfile.businessAreas.length; i++) {
      if (this.businessProfile.businessAreas[i].businessProfile === undefined) {
        this.businessProfile.businessAreas[i] = {
          name: this.businessProfile.businessAreas[i].name,
          town: this.businessProfile.businessAreas[i],
          businessProfile: {
            businessProId: this.businessProfile.businessProId
          }
        }
      }
    }
    // this.businessProfile.businessAreas = businessAreas;
    // console.log(this.businessProfile)
    const uploadImageData = new FormData();
    // for (let dbayUserImg of this.businessProfile.dbayUser.dbayUserImgsRaw) {
    //   uploadImageData.append('imageFile', dbayUserImg, dbayUserImg.name);
    // }
    if (this.businessProfile.dbayUser.dbayUserImgRaw !== undefined) {
      uploadImageData.append('imageFile', this.businessProfile.dbayUser.dbayUserImgRaw, this.businessProfile.dbayUser.dbayUserImgRaw.name);
    }
    uploadImageData.append('businessProfile', new Blob([JSON.stringify(this.businessProfile)],
      {
        type: "application/json"
      }));
    this.businessAccountService.addBusinessProfile(uploadImageData).subscribe((reply) => {
      localStorage.removeItem('br');
      this.businessAccountService.step.next(6)
    })
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(4);
  }
}
