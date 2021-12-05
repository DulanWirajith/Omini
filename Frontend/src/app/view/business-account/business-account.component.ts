import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../_service/business-account.service";

@Component({
  selector: 'app-business-account',
  templateUrl: './business-account.component.html',
  styleUrls: ['./business-account.component.css']
})
export class BusinessAccountComponent implements OnInit {

  breadCrumbTxt = 'Monitor';

  constructor(private businessAccountService: BusinessAccountService) {
    // businessAccountService.breadCrumbSub.subscribe((txt) => {
    //   this.breadCrumbTxt = txt;
    // })
  }

  ngOnInit(): void {
  }

}
