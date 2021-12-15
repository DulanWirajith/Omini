import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessRegisterService} from "../../../_service/business-register.service";

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

  businessReg;
  businessCategories = [];
  businessAreas = [];

  @ViewChild('brForm4', {static: true}) public brForm4: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    this.getBusinessCategories();
    this.getTownsWIthDistrict();
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
    }
  }

  getTownsWIthDistrict() {
    this.businessRegisterService.getTownsWIthDistrict().subscribe((businessAreas) => {
      this.businessAreas = businessAreas;
    })
  }

  getBusinessCategories() {
    this.businessRegisterService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }

  onSubmit() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(5)
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(3);
  }
}
