import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-cr-step4',
  templateUrl: './cr-step4.component.html',
  styleUrls: ['./cr-step4.component.css']
})
export class CrStep4Component implements OnInit {

  customerProfile;
  verification;

  @ViewChild('crForm4', {static: true}) public crForm4: NgForm;

  constructor(private customerAccountService: CustomerAccountService) {
    this.customerProfile = JSON.parse(JSON.stringify(customerAccountService.getNewCustomerProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
      this.customerProfile.dbayUser.verificationCode = '';
      this.sendVerification();
    }
  }

  sendVerification() {
    this.verification = '';
    this.customerAccountService.sendVerification().subscribe((user) => {
      this.verification = user.verificationCode;
    })
  }

  onSubmit() {
    const uploadImageData = new FormData();
    // for (let dbayUserImg of this.customerProfile.dbayUser.dbayUserImgsRaw) {
    //   uploadImageData.append('imageFile', dbayUserImg, dbayUserImg.name);
    // }
    if (this.customerProfile.dbayUser.dbayUserImgRaw !== undefined) {
      uploadImageData.append('imageFile', this.customerProfile.dbayUser.dbayUserImgRaw, this.customerProfile.dbayUser.dbayUserImgRaw.name);
    }
    uploadImageData.append('customerProfile', new Blob([JSON.stringify(this.customerProfile)],
      {
        type: "application/json"
      }));
    this.customerAccountService.addCustomerProfile(uploadImageData).subscribe((reply) => {
      localStorage.removeItem('cr');
      this.customerAccountService.step.next(5)
    })
  }

  previousPage() {
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
    this.customerAccountService.step.next(3);
  }

}
