import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-cr-step2',
  templateUrl: './cr-step2.component.html',
  styleUrls: ['./cr-step2.component.css']
})
export class CrStep2Component implements OnInit {

  customerProfile;

  @ViewChild('crForm2', {static: true}) public crForm2: NgForm;

  constructor(private customerAccountService: CustomerAccountService) {
    this.customerProfile = JSON.parse(JSON.stringify(customerAccountService.getNewCustomerProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
    }
  }

  onSubmit() {
    this.customerAccountService.step.next(3)
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
  }

  previousPage() {
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
    this.customerAccountService.step.next(1);
  }
}
