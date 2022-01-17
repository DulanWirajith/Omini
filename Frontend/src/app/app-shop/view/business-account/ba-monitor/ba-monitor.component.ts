import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../../../_service/login.service";

@Component({
  selector: 'app-ba-monitor',
  templateUrl: './ba-monitor.component.html',
  styleUrls: ['./ba-monitor.component.css']
})
export class BaMonitorComponent implements OnInit {

  constructor(private loginService: LoginService) {
  }

  ngOnInit(): void {
  }

  getUser() {
    return this.loginService.getUser();
  }
}
