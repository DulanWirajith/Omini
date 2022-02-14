import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {LoginService} from "../../../_service/login.service";

@Component({
  selector: 'app-customer-account',
  templateUrl: './customer-account.component.html',
  styleUrls: ['./customer-account.component.css']
})
export class CustomerAccountComponent implements OnInit {

  breadCrumbTxt;

  constructor(private router: Router, private loginService: LoginService) {
  }

  ngOnInit(): void {
    if (this.router.url.includes('customer_profile')) {
      this.breadCrumbTxt = 'Profile';
    } else if (this.router.url.includes('customer_order')) {
      this.breadCrumbTxt = 'Order';
    } else if (this.router.url.includes('customer_favourite')) {
      this.breadCrumbTxt = 'Favourite';
    } else if (this.router.url.includes('customer_following')) {
      this.breadCrumbTxt = 'Following';
    }
  }

  signOut() {
    this.loginService.signOut();
  }

  getUser() {
    return this.loginService.getUser();
  }
}
