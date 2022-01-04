import {Component, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../_service/customer-account.service";
import {ItemService} from "../../_service/item.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-item-shop-search',
  templateUrl: './item-shop-search.component.html',
  styleUrls: ['./item-shop-search.component.css']
})
export class ItemShopSearchComponent implements OnInit {

  categorySelected;
  txt;
  businessCategories = [];

  constructor(private customerAccountService: CustomerAccountService, private itemService: ItemService, private router: Router) {
  }

  ngOnInit(): void {
    this.getBusinessCategories();
  }

  searchItem() {
    if (this.categorySelected !== undefined) {
      this.itemService.getItemsPackagesBySearch(this.txt, this.categorySelected.businessCategoryId).subscribe((searchedItemPackages) => {
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['/customer/header/search_result/item_package_search_result'])
        // console.log(items)
      })
    } else {
      this.itemService.getItemsPackagesBySearch(this.txt, 'no').subscribe((searchedItemPackages) => {
        this.itemService.searchedItemPackages = searchedItemPackages;
        this.router.navigate(['/customer/header/search_result/item_package_search_result'])
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
