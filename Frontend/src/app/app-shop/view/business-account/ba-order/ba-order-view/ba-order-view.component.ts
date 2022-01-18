import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-ba-order-view',
  templateUrl: './ba-order-view.component.html',
  styleUrls: ['./ba-order-view.component.css']
})
export class BaOrderViewComponent implements OnInit {

  @Input() itemOrder;
  @Input() orderIndex;

  constructor() { }

  ngOnInit(): void {
  }

}
