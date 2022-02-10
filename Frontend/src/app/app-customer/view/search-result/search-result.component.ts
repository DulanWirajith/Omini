import {Component, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../_service/customer-account.service";
import {ItemService} from "../../_service/item.service";
import {Router} from "@angular/router";

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
  shopType;

  constructor(private customerAccService: CustomerAccountService, private itemService: ItemService, private router: Router) {
  }

  ngOnInit(): void {
    // console.log(this.router.url)
    if (this.router.url === '/customer/header/search_result/item_package_search_result') {
      this.shopType = 'Item';
    } else if (this.router.url === '/customer/header/search_result/shop_search_result') {
      this.shopType = 'Shop';
    }
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

  searchShopItems() {
    if (this.shopType === 'Item') {
      this.searchItems();
    } else if (this.shopType === 'Shop') {
      this.searchShops();
    }
  }

  searchItems() {
    this.itemService.getItemsPackagesBySearch(this.txt, this.category, this.district, this.town, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedItemPackages) => {
      this.itemService.searchedItemPackagesSub.next(searchedItemPackages);
    })
  }

  searchShops() {
    this.itemService.getShopsBySearch(this.txt, this.category, this.district, this.town, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedShops) => {
      this.itemService.searchedShopsSub.next(searchedShops);
    })
  }
}
