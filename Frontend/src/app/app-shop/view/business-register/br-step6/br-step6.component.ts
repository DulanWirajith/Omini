import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-br-step6',
  templateUrl: './br-step6.component.html',
  styleUrls: ['./br-step6.component.css']
})
export class BrStep6Component implements OnInit {

  businessProfile;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
    }
  }

  onSubmit() {

  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(5);
  }
}
