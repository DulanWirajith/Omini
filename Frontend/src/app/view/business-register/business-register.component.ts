import {Component, OnInit} from '@angular/core';
import {BusinessRegisterService} from "../../_service/business-register.service";

@Component({
  selector: 'app-business-register',
  templateUrl: './business-register.component.html',
  styleUrls: ['./business-register.component.css']
})
export class BusinessRegisterComponent implements OnInit {

  step = 1;

  constructor(private businessRegisterService: BusinessRegisterService) {
    businessRegisterService.step.subscribe((val) => {
      this.step = val;
    })
  }

  ngOnInit(): void {
  }

}
