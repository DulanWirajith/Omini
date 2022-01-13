import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-customer-account',
  templateUrl: './customer-account.component.html',
  styleUrls: ['./customer-account.component.css']
})
export class CustomerAccountComponent implements OnInit {

  breadCrumbTxt = 'Order';

  constructor() { }

  ngOnInit(): void {
  }

}
