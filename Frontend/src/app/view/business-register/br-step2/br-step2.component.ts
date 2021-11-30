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

  @ViewChild('brForm2', {static: true}) public instituteForm: NgForm;

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

  previousPage() {
    this.businessRegisterService.step.next(1);
  }
}
