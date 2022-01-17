import { Component, OnInit } from '@angular/core';
import {BusinessAccountService} from "../../../app-shop/_service/business-account.service";
import {CustomerAccountService} from "../../_service/customer-account.service";

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css']
})
export class CustomerRegisterComponent implements OnInit {

  step = 1;

  constructor(private customerAccountService: CustomerAccountService) {
    customerAccountService.step.subscribe((val) => {
      this.step = val;
    })
  }

  ngOnInit(): void {
  }

}
