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

  constructor(private customerAccountService: CustomerAccountService, private itemService: ItemService, private router: Router, private shopCartService: ShopCartService) {
  }

  ngOnInit(): void {
    this.getBusinessCategories();
  }

  searchItem() {
    if (this.categorySelected !== undefined) {
      this.itemService.getItemsPackagesBySearch(this.txt, this.categorySelected.businessCategoryId, JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedItemPackages) => {
        // console.log(searchedItemPackages)
        // for (let i = 0; i < searchedItemPackages.items.length; i++) {
        //   searchedItemPackages.items[i].orderDetail = this.shopCartService.getNewOrderDetail();
        // }
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['customer/header/search_result/item_package_search_result'])
        // console.log(items)
      })
    } else {
      this.itemService.getItemsPackagesBySearch(this.txt, 'no', JSON.parse(localStorage.getItem('user')).userId).subscribe((searchedItemPackages) => {
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['customer/header/search_result/item_package_search_result'])
        // console.log(items)
      })
    }
  }

  getBusinessCategories() {
    this.customerAccountService.getBusinessCategories().subscribe((businessCategories) => {
      // console.log(businessCategories)
      this.businessCategories = businessCategories;
    })
  }
}
