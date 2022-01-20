import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-c-order-view',
  templateUrl: './c-order-view.component.html',
  styleUrls: ['./c-order-view.component.css']
})
export class COrderViewComponent implements OnInit {

  @Input() shopItemOrders = [];
  @Input() orderIndex;

  constructor() {
  }

  ngOnInit(): void {
  }

}
