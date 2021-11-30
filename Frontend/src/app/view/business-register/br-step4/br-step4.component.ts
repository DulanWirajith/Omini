import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-br-step4',
  templateUrl: './br-step4.component.html',
  styleUrls: ['./br-step4.component.css']
})
export class BrStep4Component implements OnInit {

  title = "geeksforgeeks-multiSelect";

  cars = [
    {id: 1, name: "BMW Hyundai"},
    {id: 2, name: "Kia Tata"},
    {id: 3, name: "Volkswagen Ford"},
    {id: 4, name: "Renault Audi"},
    {id: 5, name: "Mercedes Benz Skoda"},
  ];

  selected = [{id: 3, name: "Volkswagen Ford"}];

  constructor() {
  }

  ngOnInit(): void {
  }

}
