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

  districts = [];
  towns = [];
  shopTypes = [];
  txt;
  category = '0';
  district = '0';
  town = '0';
  shopType;

  constructor(private customerAccService: CustomerAccountService, private itemService: ItemService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // console.log(this.router)
    if (this.router.url.split('?')[0] === '/customer/header/search_result/item_package_search_result') {
      this.shopType = 'Item';
    } else if (this.router.url.split('?')[0] === '/customer/header/search_result/shop_search_result') {
      this.shopType = 'Shop';
    }
    this.getDistricts();
    this.getBusinessCategories();
    if (this.route.snapshot.queryParamMap.get('txt') !== null) {
      this.txt = this.route.snapshot.queryParamMap.get('txt');
      if (this.route.snapshot.queryParamMap.get('category') !== null) {
        this.category = this.route.snapshot.queryParamMap.get('category');
      }
      if (this.route.snapshot.queryParamMap.get('district') !== null) {
        this.district = this.route.snapshot.queryParamMap.get('district');
      }
      if (this.route.snapshot.queryParamMap.get('town') !== null) {
        this.town = this.route.snapshot.queryParamMap.get('town');
      }
      this.searchItems(1);
      this.searchShops(1);
    }
  }

  getDistricts() {
    this.customerAccService.getDistricts('C001').subscribe((districts) => {
      this.districts = districts;
      this.getTowns(this.district)
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
        this.town = '0';
      }
    })
  }

  searchShopItems() {
    if (this.shopType === 'Item') {
      this.searchItems(0);
    } else if (this.shopType === 'Shop') {
      this.searchShops(0);
    }
  }

  routeToItem() {
    this.shopType = 'Item';
    this.router.navigate(['customer/header/search_result/item_package_search_result'], {
      queryParams: {
        'txt': this.txt,
        'category': this.category,
        'district': this.district,
        'town': this.town
      }
    })
    if (this.itemService.searchedItemPackages.items.length === 0 || this.itemService.searchedItemPackages.itemPackages.length === 0) {
      this.searchItems(1)
    }
  }

  searchItems(val) {
    let userId = 0;
    if (this.getUser() !== null) {
      userId = this.getUser().userId;
    }
    this.itemService.getItemsPackagesBySearch(this.txt, this.category, this.district, this.town, userId).subscribe((searchedItemPackages) => {
      this.itemService.searchedItemPackagesSub.next(searchedItemPackages);
    })
    const queryParams = {
      'txt': this.txt,
      'category': this.category,
      'district': this.district,
      'town': this.town
    };
    if (val === 0) {
      this.router.navigate(
        [],
        {
          relativeTo: this.route,
          queryParams: queryParams,
          queryParamsHandling: 'merge', // remove to replace all query params by provided
        });
    }
  }

  routeToShop() {
    this.shopType = 'Shop';
    this.router.navigate(['customer/header/search_result/shop_search_result'], {
      queryParams: {
        'txt': this.txt,
        'category': this.category,
        'district': this.district,
        'town': this.town
      }
    })
    if (this.itemService.searchedShops.length === 0) {
      this.searchShops(1);
    }
  }

  searchShops(val) {
    let userId = 0;
    if (this.getUser() !== null) {
      userId = this.getUser().userId;
    }
    this.itemService.getShopsBySearch(this.txt, this.category, this.district, this.town, userId).subscribe((searchedShops) => {
      this.itemService.searchedShopsSub.next(searchedShops);
    })
    if (val === 0) {
      const queryParams = {
        'txt': this.txt,
        'category': this.category,
        'district': this.district,
        'town': this.town
      };

      this.router.navigate(
        [],
        {
          relativeTo: this.route,
          queryParams: queryParams,
          queryParamsHandling: 'merge', // remove to replace all query params by provided
        });
    }
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  getUrl() {
    return this.router.url.split('?')[0];
  }
}
