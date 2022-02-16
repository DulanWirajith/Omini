import {Component, OnDestroy, OnInit} from '@angular/core';
import {CustomerAccountService} from "../../../_service/customer-account.service";
import * as $ from "jquery";
import {ProfileGService} from "../../../../_service/profile-g.service";
import {Router} from "@angular/router";
import {LoginService} from "../../../../_service/login.service";

@Component({
  selector: 'app-c-following',
  templateUrl: './c-following.component.html',
  styleUrls: ['./c-following.component.css']
})
export class CFollowingComponent implements OnInit {

  static lastComp: CFollowingComponent;
  businesses = [];

  constructor(private customerAccService: CustomerAccountService, private profileService: ProfileGService, private router: Router, private loginService: LoginService) {
    // if (CFollowingComponent.lastComp === undefined) {
    //   this.customerAccService.getFollowedBusinesses(JSON.parse(localStorage.getItem('user')).userId).subscribe((businesses) => {
    //     // console.log(businesses)
    //     this.businesses = businesses;
    //   })
    // } else {
    //   return CFollowingComponent.lastComp;
    // }
  }

  ngOnInit(): void {
    this.customerAccService.getFollowedBusinesses(JSON.parse(localStorage.getItem('user')).userId).subscribe((businesses) => {
      // console.log(businesses)
      this.businesses = businesses;
    })
  }

  routeToShop(profileId) {
    this.profileService.profile.profileId = profileId;
    this.profileService.profile.returnUrl = '/customer/header/customer_account/customer_following';
    this.profileService.profile.breadCrumb = ['Account', 'Following'];
    this.router.navigate(['/shop/header/shop_view'])
    localStorage.setItem('shop-view', JSON.stringify({id: this.profileService.profile.profileId}))
  }

  followBusiness(businessProfile, index) {
    this.customerAccService.followBusiness(JSON.parse(localStorage.getItem('user')).userId, businessProfile.businessProId).subscribe((followed) => {
      this.businesses.splice(index, 1)
    })
  }
}
