import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";

@Component({
  selector: 'app-c-order',
  templateUrl: './c-order.component.html',
  styleUrls: ['./c-order.component.css']
})
export class COrderComponent implements OnInit {

  itemOrders = [];

  constructor(private itemService: ItemService) {
  }

  ngOnInit(): void {
    this.getPendingCustomerOrders();
  }

  getPendingCustomerOrders() {
    this.itemService.getPendingCustomerOrders('U20220102233339').subscribe((itemOrders) => {
      this.itemOrders = itemOrders;
    })
  }
}
