import {Component, OnInit, ViewChild} from '@angular/core';
import {BusinessRegisterService} from "../../../_service/business-register.service";

@Component({
  selector: 'app-ba-manage',
  templateUrl: './ba-manage.component.html',
  styleUrls: ['./ba-manage.component.css']
})
export class BaManageComponent implements OnInit {


  constructor(private businessRegisterService: BusinessRegisterService) {

  }

  ngOnInit(): void {

  }


}
