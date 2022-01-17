import { Component, OnInit } from '@angular/core';
import {BusinessAccountService} from "../../../../app-shop/_service/business-account.service";

@Component({
  selector: 'app-cr-step5',
  templateUrl: './cr-step5.component.html',
  styleUrls: ['./cr-step5.component.css']
})
export class CrStep5Component implements OnInit {

  customerProfile;

  constructor(private businessAccountService: BusinessAccountService) {
    this.customerProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
    }
  }

  // onSubmit() {
  //
  // }

  previousPage() {
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
    this.businessAccountService.step.next(4);
  }

}
