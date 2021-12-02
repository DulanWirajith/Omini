import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../_service/business-register.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-br-step6',
  templateUrl: './br-step6.component.html',
  styleUrls: ['./br-step6.component.css']
})
export class BrStep6Component implements OnInit {

  businessReg;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
    }
  }

  onSubmit() {

  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(5);
  }
}
