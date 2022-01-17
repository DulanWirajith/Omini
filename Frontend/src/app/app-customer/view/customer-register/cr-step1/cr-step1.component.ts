import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../../app-shop/_service/business-account.service";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-cr-step1',
  templateUrl: './cr-step1.component.html',
  styleUrls: ['./cr-step1.component.css']
})
export class CrStep1Component implements OnInit {

  customerProfile;

  @ViewChild('crForm1', {static: true}) public crForm1: NgForm;

  constructor(private customerAccountService: CustomerAccountService) {
    this.customerProfile = JSON.parse(JSON.stringify(customerAccountService.getNewCustomerProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
    }
  }

  onSubmit() {
    this.customerAccountService.step.next(2)
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
  }

}
