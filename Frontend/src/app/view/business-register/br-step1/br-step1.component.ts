import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {BusinessRegisterService} from "../../../_service/business-register.service";

@Component({
  selector: 'app-br-step1',
  templateUrl: './br-step1.component.html',
  styleUrls: ['./br-step1.component.css']
})
export class BrStep1Component implements OnInit {

  businessReg;

  @ViewChild('brForm1', {static: true}) public instituteForm: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
    } else {
      this.businessReg = businessRegisterService.getNewBR();
    }
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.businessRegisterService.step.next(2)
    localStorage.setItem('br', JSON.stringify(this.businessReg));
  }

}
