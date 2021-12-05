import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessRegisterService} from "../../../_service/business-register.service";

@Component({
  selector: 'app-br-step3',
  templateUrl: './br-step3.component.html',
  styleUrls: ['./br-step3.component.css']
})
export class BrStep3Component implements OnInit {

  businessReg;

  @ViewChild('brForm3', {static: true}) public instituteForm: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
    }
  }

  onSubmit() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(4)
  }

  previousPage() {
    localStorage.setItem('br', JSON.stringify(this.businessReg));
    this.businessRegisterService.step.next(2);
  }
}
