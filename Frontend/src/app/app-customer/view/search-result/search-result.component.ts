import {Component, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../_service/customer-account.service";
import {ItemService} from "../../_service/item.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ShopCartService} from "../../_service/shop-cart.service";

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  regions = [];
  towns = [];
  shopTypes = [];
  txt;
  category = '0';
  region = '0';
  town = '0';
  shopType;
  itemIsSearched = false;
  shopIsSearched = false;

  constructor(private customerAccService: CustomerAccountService, private itemService: ItemService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // console.log(this.router)
    if (this.router.url === '/customer/header/search_result/item_package_search_result') {
      this.shopType = 'Item';
      this.itemIsSearched = true;
    } else if (this.router.url === '/customer/header/search_result/shop_search_result') {
      this.shopType = 'Shop';
      this.shopIsSearched = true;
    }
    this.getDistricts();
    this.getBusinessCategories();
    if (this.getUrl().txt !== undefined) {
      this.txt = this.getUrl().txt;
      if (this.getUrl().category !== undefined) {
        this.category = this.getUrl().category;
      }
      if (this.getUrl().region !== undefined) {
        this.region = this.getUrl().region;
      }
      if (this.getUrl().town !== undefined) {
        this.town = this.getUrl().town;
      }
      this.searchItems(1);
      this.searchShops(1);
    }
  }

  getDistricts() {
    this.customerAccService.getDistricts('C001').subscribe((regions) => {
      this.regions = regions;
      this.getTowns(this.region)
    })
  }

  getBusinessCategories() {
    this.customerAccService.getBusinessCategories().subscribe((shopTypes) => {
      this.shopTypes = shopTypes;
    })
  }

  getTowns(regionId) {
    // console.log(regionId)
    this.region = regionId;
    this.customerAccService.getTowns(regionId).subscribe((towns) => {
      this.towns = towns;
      if (towns.length === 0) {
        this.town = '0';
      }
    })
  }

  searchShopItems() {
    if (this.shopType === 'Item') {
      this.searchItems(0);
      this.itemIsSearched = true;
    } else if (this.shopType === 'Shop') {
      this.searchShops(0);
      this.shopIsSearched = true;
    }
  }

  routeToItem() {
    this.shopType = 'Item';
    this.router.navigate(['customer/header/search_result/item_package_search_result'])
    localStorage.setItem('item-shop-search', JSON.stringify(
      {
        txt: this.txt,
        category: this.category,
        region: this.region,
        town: this.town
      }
    ))
    if (this.shopIsSearched) {
      this.shopIsSearched = false;
      this.searchItems(1)
    }
  }

  searchItems(val) {
    let userId = 0;
    if (this.getUser() !== null) {
      userId = this.getUser().userId;
    }
    this.itemService.getItemsPackagesBySearch(this.txt, this.category, this.region, this.town, userId).subscribe((searchedItemPackages) => {
      this.itemService.searchedItemPackagesSub.next(searchedItemPackages);
    })
    if (val === 0) {
      localStorage.setItem('item-shop-search', JSON.stringify(
        {
          txt: this.txt,
          category: this.category,
          region: this.region,
          town: this.town
        }
      ))
    }
  }

  routeToShop() {
    this.shopType = 'Shop';
    this.router.navigate(['customer/header/search_result/shop_search_result'])
    localStorage.setItem('item-shop-search', JSON.stringify(
      {
        txt: this.txt,
        category: this.category,
        region: this.region,
        town: this.town
      }
    ))
    if (this.itemIsSearched) {
      this.itemIsSearched = false;
      this.searchShops(1);
    }
  }

  searchShops(val) {
    let userId = 0;
    if (this.getUser() !== null) {
      userId = this.getUser().userId;
    }
    this.itemService.getShopsBySearch(this.txt, this.category, this.region, this.town, userId).subscribe((searchedShops) => {
      this.itemService.searchedShopsSub.next(searchedShops);
    })
    if (val === 0) {
      localStorage.setItem('item-shop-search', JSON.stringify(
        {
          txt: this.txt,
          category: this.category,
          region: this.region,
          town: this.town
        }
      ))
    }
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  getUrl() {
    return JSON.parse(localStorage.getItem('item-shop-search'));
  }

  getCurUrl() {
    return this.router.url;
  }
}
