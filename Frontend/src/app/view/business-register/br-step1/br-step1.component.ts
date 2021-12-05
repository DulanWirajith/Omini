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

  @ViewChild('brForm1', {static: true}) public brForm1: NgForm;

  constructor(private businessRegisterService: BusinessRegisterService) {
    this.businessReg = JSON.parse(JSON.stringify(businessRegisterService.getNewBR()));
  }

  ngOnInit(): void {
    if (localStorage.getItem('br') !== null) {
      this.businessReg = JSON.parse(localStorage.getItem('br'));
    }
  }

  onSubmit() {
    this.businessRegisterService.step.next(2)
    localStorage.setItem('br', JSON.stringify(this.businessReg));
  }

}
