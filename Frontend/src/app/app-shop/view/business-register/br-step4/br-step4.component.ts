import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-br-step4',
  templateUrl: './br-step4.component.html',
  styleUrls: ['./br-step4.component.css']
})
export class BrStep4Component implements OnInit {

  // title = "geeksforgeeks-multiSelect";

  // cars = [
  //   {id: 1, name: "BMW Hyundai"},
  //   {id: 2, name: "Kia Tata"},
  //   {id: 3, name: "Volkswagen Ford"},
  //   {id: 4, name: "Renault Audi"},
  //   {id: 5, name: "Mercedes Benz Skoda"},
  // ];
  //
  // selected = [{id: 3, name: "Volkswagen Ford"}];

  businessProfile;
  businessCategories = [];
  businessAreas = [];

  @ViewChild('brForm4', {static: true}) public brForm4: NgForm;

  constructor(private businessAccountService: BusinessAccountService) {
    this.businessProfile = JSON.parse(JSON.stringify(businessAccountService.getNewBusinessProfile()));
  }

  ngOnInit(): void {
    this.getBusinessCategories();
    this.getTownsWIthDistrict();
    if (localStorage.getItem('br') !== null) {
      this.businessProfile = JSON.parse(localStorage.getItem('br'));
    }
  }

  getTownsWIthDistrict() {
    this.businessAccountService.getTownsWIthDistrict().subscribe((businessAreas) => {
      this.businessAreas = businessAreas;
    })
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  onSubmit() {
    if (this.businessProfile.businessProfileCategories.length > 0) {
      if (this.businessProfile.businessProfileCategories[0].businessProfile === undefined) {
        this.businessProfile.businessProfileCategories[0] = {
          name: this.businessProfile.businessProfileCategories[0].name,
          businessCategory: this.businessProfile.businessProfileCategories[0],
          businessProfile: {
            businessProId: this.businessProfile.businessProId
          }
        }
      }
      this.businessProfile.defaultBusiness = this.businessProfile.businessProfileCategories[0].businessCategory;
    }
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(5)
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessProfile));
    this.businessAccountService.step.next(3);
  }
}
