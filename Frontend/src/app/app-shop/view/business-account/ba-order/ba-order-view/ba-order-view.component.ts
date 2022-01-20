import {Component, Input, OnInit} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";

@Component({
  selector: 'app-ba-order-view',
  templateUrl: './ba-order-view.component.html',
  styleUrls: ['./ba-order-view.component.css']
})
export class BaOrderViewComponent implements OnInit {

  @Input() itemOrder;
  @Input() orderIndex;

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
  }

  acceptItem(orderDetailId) {
    this.itemService.acceptItem(orderDetailId).subscribe((reply) => {
      if(reply){

      }
    })
  }
}
