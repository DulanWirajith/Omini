import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../_service/business-register.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-br-step2',
  templateUrl: './br-step2.component.html',
  styleUrls: ['./br-step2.component.css']
})
export class BrStep2Component implements OnInit {

  businessReg;
  countries;
  districts;
  towns;

  @ViewChild('brForm2', {static: true}) public brForm2: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    this.getCountries();
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
      if (this.businessReg.country !== '' && this.businessReg.district !== '') {
        this.getDistricts(this.businessReg.country)
        this.getTowns(this.businessReg.district)
      }
    }
  }

  getCountries() {
    this.businessRegisterService.getCountries().subscribe((countries) => {
      this.countries = countries;
    })
  }

  getDistricts(countryId) {
    this.businessRegisterService.getDistricts(countryId).subscribe((districts) => {
      this.districts = districts;
    })
  }

  getTowns(districtId) {
    this.businessRegisterService.getTowns(districtId).subscribe((towns) => {
      this.towns = towns;
    })
  }

  onSubmit() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(3)
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(1);
  }
}
