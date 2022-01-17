import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../../../_service/login.service";

@Component({
  selector: 'app-customer-account',
  templateUrl: './customer-account.component.html',
  styleUrls: ['./customer-account.component.css']
})
export class CustomerAccountComponent implements OnInit {

  breadCrumbTxt = 'Order';

  constructor(private router: Router, private loginService: LoginService) {
  }

  ngOnInit(): void {
  }

  signOut() {
    this.loginService.signOut();
  }

  getUser() {
    return this.loginService.getUser();
  }
}
