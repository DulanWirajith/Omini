import {Component, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../_service/customer-account.service";
import {ItemService} from "../../_service/item.service";
import {Router} from "@angular/router";
import {ShopCartService} from "../../_service/shop-cart.service";

@Component({
  selector: 'app-item-shop-search',
  templateUrl: './item-shop-search.component.html',
  styleUrls: ['./item-shop-search.component.css']
})
export class ItemShopSearchComponent implements OnInit {

  categorySelected;
  txt;
  businessCategories = [];
  shopType = 'Item';

  constructor(private customerAccountService: CustomerAccountService, private itemService: ItemService, private router: Router, private shopCartService: ShopCartService) {
  }

  ngOnInit(): void {
    this.getBusinessCategories();
  }

  searchShopItems() {
    if (this.shopType === 'Item') {
      this.searchItems();
    } else if (this.shopType === 'Shop') {
      this.searchShops();
    }
  }

  searchItems() {
    if (this.categorySelected !== undefined) {
      this.itemService.getItemsPackagesBySearch(this.txt, this.categorySelected.businessCategoryId, 0, 0, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedItemPackages) => {
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['customer/header/search_result/item_package_search_result'])
        // console.log(items)
      })
    } else {
      let customerId = '0';
      if (JSON.parse(localStorage.getItem('user')) !== null) {
        customerId = JSON.parse(localStorage.getItem('user')).userId;
      }
      this.itemService.getItemsPackagesBySearch(this.txt, '0', 0, 0, customerId).subscribe((searchedItemPackages) => {
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['customer/header/search_result/item_package_search_result'])
        // console.log(items)
      })
    }
  }

  searchShops() {
    if (this.categorySelected !== undefined) {
      this.itemService.getShopsBySearch(this.txt, this.categorySelected.businessCategoryId, 0, 0, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedShops) => {
        this.itemService.searchedShops = searchedShops;
        this.router.navigate(['customer/header/search_result/shop_search_result'])
        // console.log(items)
      })
    } else {
      let customerId = '0';
      if (JSON.parse(localStorage.getItem('user')) !== null) {
        customerId = JSON.parse(localStorage.getItem('user')).userId;
      }
      this.itemService.getShopsBySearch(this.txt, '0', 0, 0, customerId).subscribe((searchedShops) => {
        this.itemService.searchedShops = searchedShops;
        this.router.navigate(['customer/header/search_result/shop_search_result'])
        // console.log(items)
      })
    }
  }

  getBusinessCategories() {
    this.customerAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
    })
  }
}
