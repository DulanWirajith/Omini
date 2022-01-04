import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppCustomerComponent} from './app-customer.component';
import {HeaderComponent} from "../view/header/header.component";
import {CustomerProfileComponent} from "./view/customer-profile/customer-profile.component";
import {CpProfileSettingsComponent} from "./view/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {ItemShopSearchComponent} from "./view/item-shop-search/item-shop-search.component";
import {SearchResultComponent} from "./view/search-result/search-result.component";
import {ItemPackageSearchResultComponent} from "./view/search-result/item-package-search-result/item-package-search-result.component";

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
        path: 'customer_profile',
        component: CustomerProfileComponent,
        children: [
          {
            path: 'profile_settings',
            component: CpProfileSettingsComponent,
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
