import {Component, Input, OnInit} from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {ItemGService} from "../../../../../_service/item-g.service";


@Component({
  selector: 'app-ba-order-view',
  templateUrl: './ba-order-view.component.html',
  styleUrls: ['./ba-order-view.component.css']
})
export class BaOrderViewComponent implements OnInit {

  @Input() itemOrder;
  @Input() orderIndex;

  constructor(private itemService: ItemService, private itemServiceG: ItemGService) {
  }

  ngOnInit(): void {
  }

  acceptItem(orderDetailId) {
    this.itemService.acceptItem(orderDetailId).subscribe((reply) => {
      if (reply) {

      }
    })
  }

  getItemSelected(item) {
    this.itemServiceG.itemSub.next({
      item: item,
      backBtn: undefined
    });
  }

  getItemPackageSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined
    });
  }
}
