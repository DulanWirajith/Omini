import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../_service/business-account.service";
import {ItemService} from "../../_service/item.service";
import {Router} from "@angular/router";
import {ShopCartService} from "../../../app-customer/_service/shop-cart.service";
import {LoginService} from "../../../_service/login.service";

@Component({
  selector: 'app-business-account',
  templateUrl: './business-account.component.html',
  styleUrls: ['./business-account.component.css']
})
export class BusinessAccountComponent implements OnInit {

  breadCrumbTxt = 'Monitor';
  businessCategories = [];
  businessProfileCategory;
  businessCategory = {
    businessCategoryId: '',
    name: ''
  };
  navOrder: 0;

  constructor(private businessAccountService: BusinessAccountService, private itemService: ItemService, private router: Router, private loginService: LoginService) {
    // businessAccountService.navBarSub.subscribe((val) => {
    //   if (val.name === 'Order') {
    //     this.navOrder = val.value;
    //   }
    //   // this.breadCrumbTxt = txt;
    // })
    this.businessAccountService.navBarSub.observers = [];
    this.businessAccountService.navBarSub.subscribe((val) => {
      //console.log(val)
      if (val.name === 'Business') {
        this.businessCategory = val.value;
        this.setBusinessCategory();
        // this.updateNavBar();
      }
    })
    this.getBusinessCategories();
  }

  ngOnInit(): void {
  }

  getBusinessCategories() {
    this.businessAccountService.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategories = businessCategories;
      this.businessAccountService.businessCategories = businessCategories;
      this.businessAccountService.businessCategoriesSub.next(businessCategories);
      this.businessCategory = this.loginService.getUser().businessProfile.defaultBusiness;
      // this.businessAccountService.businessCategory = this.businessCategory;
      this.setBusinessCategory();
    })
  }

  setBusinessCategory() {
    // console.log(this.businessCategory.businessCategoryId)
    this.businessAccountService.businessCategorySub.next(this.businessCategory.businessCategoryId);
    this.businessAccountService.businessCategory = this.businessCategory;
    this.updateNavBar();
    // this.getNavBar();
    // this.getItemOrders();

  }

  updateNavBar() {
    this.businessAccountService.getNavBar(this.loginService.getUser().userId, this.businessCategory.businessCategoryId).subscribe((user) => {
      // console.log(user.businessProfile.countPendingOrders)
      this.loginService.getUser().businessProfile.countPendingOrders = user.businessProfile.countPendingOrders;
      localStorage.setItem('user', JSON.stringify(this.loginService.getUser()));
    })
  }

  // getItemOrders() {
  // this.itemService.getItemOrders(this.loginService.getUser().userId, this.businessCategory.businessCategoryId, 'Pending').subscribe((itemOrders) => {
  //   this.businessAccountService.itemOrders = itemOrders;
  //   this.navOrder = itemOrders.length;
  //   this.businessAccountService.navBarSub.next('Order');
  //   // this.router.navigate(['/shop/header/business_account/ba_order'])
  // })
  // }

  signOut() {
    this.loginService.signOut();
  }

  getUser() {
    // console.log(this.loginService.getUser())
    return this.loginService.getUser();
  }

  getItemPackagesOrdered() {
    // this.itemService.getItemsOrdered("B321", this.businessProfileCategory.businessCategoryId, 0, 100).subscribe((items) => {
    //   this.items = items;
    //   for (let item of this.items) {
    //     item.itemImgsRaw = [];
    //     item.businessProfileCategory = {
    //       businessProfile: undefined,
    //       businessCategory: undefined
    //     }
    //   }
    //   //console.log(this.items)
    // })
  }
}
