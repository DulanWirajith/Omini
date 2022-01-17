import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-cr-step3',
  templateUrl: './cr-step3.component.html',
  styleUrls: ['./cr-step3.component.css']
})
export class CrStep3Component implements OnInit {

  customerProfile;
  countries;
  districts;
  towns;

  @ViewChild('crForm3', {static: true}) public crForm3: NgForm;

  constructor(private customerAccountService: CustomerAccountService) {
    this.customerProfile = JSON.parse(JSON.stringify(customerAccountService.getNewCustomerProfile()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
    }
    this.getCountries();
    if (localStorage.getItem('cr') !== null) {
      this.customerProfile = JSON.parse(localStorage.getItem('cr'));
      if (this.customerProfile.country !== '' && this.customerProfile.district !== '') {
        this.getDistricts(this.customerProfile.country)
        this.getTowns(this.customerProfile.district)
      }
    }
  }

  getCountries() {
    this.customerAccountService.getCountries().subscribe((countries) => {
      this.countries = countries;
    })
  }

  getDistricts(countryId) {
    this.customerAccountService.getDistricts(countryId).subscribe((districts) => {
      this.districts = districts;
    })
  }

  getTowns(districtId) {
    this.customerAccountService.getTowns(districtId).subscribe((towns) => {
      this.towns = towns;
    })
  }

  onSubmit() {
    this.customerAccountService.step.next(4)
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
  }

  previousPage() {
    localStorage.setItem('cr', JSON.stringify(this.customerProfile));
    this.customerAccountService.step.next(2);
  }

}
