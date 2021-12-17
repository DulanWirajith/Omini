import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-br-step2',
  templateUrl: './br-step2.component.html',
  styleUrls: ['./br-step2.component.css']
})
export class BrStep2Component implements OnInit {

  businessProfile;
  countries;
  districts;
  towns;

  @ViewChild('brForm2', {static: true}) public brForm2: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    this.getCountries();
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
      if (this.businessProfile.country !== '' && this.businessProfile.district !== '') {
        this.getDistricts(this.businessProfile.country)
        this.getTowns(this.businessProfile.district)
      }
    }
  }

  getCountries() {
    this.businessAccountService.getCountries().subscribe((countries) => {
      this.countries = countries;
    })
  }

  getDistricts(countryId) {
    this.businessAccountService.getDistricts(countryId).subscribe((districts) => {
      this.districts = districts;
    })
  }

  getTowns(districtId) {
    this.businessAccountService.getTowns(districtId).subscribe((towns) => {
      this.towns = towns;
    })
  }

  onSubmit() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(3)
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(1);
  }
}
