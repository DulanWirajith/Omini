import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../_service/business-register.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-br-step5',
  templateUrl: './br-step5.component.html',
  styleUrls: ['./br-step5.component.css']
})
export class BrStep5Component implements OnInit {

  businessReg;

  @ViewChild('brForm5', {static: true}) public brForm5: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
      this.businessReg.user.verificationCode = '';
    }
  }

  onSubmit() {
    let businessTypes = [];
    let businessAreas = [];
    for (let businessType of this.businessReg.businessProfileCategories) {
      businessTypes.push({
        businessCategory: businessType
      })
    }
    this.businessReg.businessProfileCategories = businessTypes;

    for (let businessArea of this.businessReg.businessAreas) {
      businessAreas.push({
        town: businessArea
      })
    }
    this.businessReg.businessAreas = businessAreas;
    console.log(this.businessReg)
    this.businessRegisterService.addBusinessProfile(this.businessReg).subscribe((reply) => {
      localStorage.removeItem('br');
      this.businessRegisterService.step.next(6)
    })
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(4);
  }
}
