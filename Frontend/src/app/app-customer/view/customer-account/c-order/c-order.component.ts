import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {ShopCartService} from "../../../_service/shop-cart.service";
import {LoginService} from "../../../../_service/login.service";

@Component({
  selector: 'app-c-order',
  templateUrl: './c-order.component.html',
  styleUrls: ['./c-order.component.css']
})
export class COrderComponent implements OnInit {

  itemOrders = [];

  constructor(private itemService: ItemService, private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.getPendingCustomerOrders();
  }

  getPendingCustomerOrders() {
    this.itemService.getPendingCustomerOrders(this.loginService.getUser().userId).subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
    })
  }
}
