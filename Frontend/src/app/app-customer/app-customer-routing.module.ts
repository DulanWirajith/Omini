import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppCustomerComponent} from './app-customer.component';
import {HeaderComponent} from "../view/header/header.component";
import {ItemShopSearchComponent} from "./view/item-shop-search/item-shop-search.component";
import {SearchResultComponent} from "./view/search-result/search-result.component";
import {ItemPackageSearchResultComponent} from "./view/search-result/item-package-search-result/item-package-search-result.component";
import {CustomerAccountComponent} from "./view/customer-account/customer-account.component";
import {CProfileComponent} from "./view/customer-account/c-profile/c-profile.component";
import {COrderComponent} from "./view/customer-account/c-order/c-order.component";
import {BusinessRegisterComponent} from "../app-shop/view/business-register/business-register.component";
import {CustomerRegisterComponent} from "./view/customer-register/customer-register.component";
import {CFavouriteComponent} from "./view/customer-account/c-favourite/c-favourite.component";
import {ShopSearchResultComponent} from "./view/search-result/shop-search-result/shop-search-result.component";
import {ShopViewComponent} from "../view/shop-view/shop-view.component";
import {CFollowingComponent} from "./view/customer-account/c-following/c-following.component";

const routes: Routes = [
  // {
  //   path: 'customer',
  //   redirectTo: '/customer/header/customer_account/customer_order',
  //   pathMatch: 'full'
  // },
  {
    path: 'customer_register',
    component: CustomerRegisterComponent,
  },
  {
    path: 'header',
    component: AppCustomerComponent,
    children: [
      {
        path: 'customer_account',
        component: CustomerAccountComponent,
        children: [
          {
            path: 'customer_profile',
            component: CProfileComponent,
          },
          {
            path: 'customer_order',
            component: COrderComponent,
          },
          {
            path: 'customer_favourite',
            component: CFavouriteComponent,
          },
          {
            path: 'customer_following',
            component: CFollowingComponent,
          }
        ]
      },
      {
        path: 'item_shop_search',
        component: ItemShopSearchComponent,
      },
      {
        path: 'search_result',
        component: SearchResultComponent,
        children: [
          {
            path: 'item_package_search_result',
            component: ItemPackageSearchResultComponent,
          },
          {
            path: 'shop_search_result',
            component: ShopSearchResultComponent,
          }
        ]
      },
      {
        path: 'shop_view',
        component: ShopViewComponent,
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppCustomerRoutingModule {
}
