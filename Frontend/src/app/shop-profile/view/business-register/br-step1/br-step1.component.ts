import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-br-step1',
  templateUrl: './br-step1.component.html',
  styleUrls: ['./br-step1.component.css']
})
export class BrStep1Component implements OnInit {

  businessProfile;

  @ViewChild('brForm1', {static: true}) public brForm1: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
    }
  }

  onSubmit() {
    this.businessAccountService.step.next(2)
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
  }

}
