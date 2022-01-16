import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-customer-account',
  templateUrl: './customer-account.component.html',
  styleUrls: ['./customer-account.component.css']
})
export class CustomerAccountComponent implements OnInit {

  breadCrumbTxt = 'Order';

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  signOut() {
    localStorage.clear();
    this.router.navigate([''])
  }
}
