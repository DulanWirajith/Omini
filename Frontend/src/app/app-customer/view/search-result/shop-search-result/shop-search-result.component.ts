import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {Router} from "@angular/router";
import {ProfileGService} from "../../../../_service/profile-g.service";
import {CustomerAccountService} from "../../../_service/customer-account.service";

@Component({
  selector: 'app-shop-search-result',
  templateUrl: './shop-search-result.component.html',
  styleUrls: ['./shop-search-result.component.css']
})
export class ShopSearchResultComponent implements OnInit {

  shops: any = [];

  constructor(private itemService: ItemService, private router: Router, private profileService: ProfileGService, private customerAccService: CustomerAccountService) {
    this.itemService.searchedShopsSub.subscribe((shops) => {
      this.itemService.searchedShops = shops;
      this.shops = shops;
    })
  }

  ngOnInit(): void {
    this.shops = this.itemService.searchedShops;
    // console.log(this.shops)
  }

  routeToShop(shop, profileId) {
    this.profileService.profile.profileId = profileId;
    this.profileService.profile.business = shop;
    this.profileService.profile.returnUrl = '/customer/header/search_result/shop_search_result';
    this.router.navigate(['/shop/header/shop_view'])
  }

  followBusiness(businessProfile) {
    this.customerAccService.followBusiness(JSON.parse(localStorage.getItem('user')).userId, businessProfile.businessProId).subscribe((followed) => {
      businessProfile.followed = followed;
    })
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}
