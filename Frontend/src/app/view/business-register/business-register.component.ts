import {Component, OnInit} from '@angular/core';
import {BusinessRegisterService} from "../../_service/business-register.service";
import {BusinessAccountService} from "../../_service/business-account.service";

@Component({
  selector: 'app-business-register',
  templateUrl: './business-register.component.html',
  styleUrls: ['./business-register.component.css']
})
export class BusinessRegisterComponent implements OnInit {

  step = 1;

  constructor(private businessAccountService: BusinessAccountService) {
    businessAccountService.step.subscribe((val) => {
      this.step = val;
    })
  }

  ngOnInit(): void {
  }

}
