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

const routes: Routes = [
  {
    path: '',
    redirectTo: 'profile_settings',
    pathMatch: 'full'
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
          }
        ]
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppCustomerRoutingModule {
}
