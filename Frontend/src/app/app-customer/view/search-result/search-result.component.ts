import {Component, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../_service/customer-account.service";
import {ItemService} from "../../_service/item.service";

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  districts = [];
  towns = [];
  shopTypes = [];
  txt;
  category = 0;
  district = 0;
  town = 0;

  constructor(private customerAccService: CustomerAccountService, private itemService: ItemService) {
  }

  ngOnInit(): void {
    this.getDistricts();
    this.getBusinessCategories();
  }

  getDistricts() {
    this.customerAccService.getDistricts('C001').subscribe((districts) => {
      this.districts = districts;
    })
  }

  getBusinessCategories() {
    this.customerAccService.getBusinessCategories().subscribe((shopTypes) => {
      this.shopTypes = shopTypes;
    })
  }

  getTowns(districtId) {
    // console.log(districtId)
    this.district = districtId;
    this.customerAccService.getTowns(districtId).subscribe((towns) => {
      this.towns = towns;
      if (towns.length === 0) {
        this.town = 0;
      }
    })
  }

  searchItems() {
    this.itemService.getItemsPackagesBySearch(this.txt, this.category, this.district, this.town, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedItemPackages) => {
      this.itemService.searchedItemPackagesSub.next(searchedItemPackages);
    })
  }
}
