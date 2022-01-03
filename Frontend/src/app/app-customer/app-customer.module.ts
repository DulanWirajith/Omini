import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AppCustomerRoutingModule} from './app-customer-routing.module';
import {AppCustomerComponent} from './app-customer.component';
import {CpProfileSettingsComponent} from "./view/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {SharedModule} from "../_shared/shared.module";
import {CustomerProfileComponent} from "./view/customer-profile/customer-profile.component";
import {ItemShopSearchComponent} from "./view/item-shop-search/item-shop-search.component";
import {ItemSearchResultComponent} from "./view/search-result/item-search-result/item-search-result.component";
import {CustomerAccountComponent} from "./view/customer-account/customer-account.component";
import {SearchResultComponent} from "./view/search-result/search-result.component";
import {ItemSearchResultViewComponent} from "./view/search-result/item-search-result/item-search-result-view/item-search-result-view.component";

const components = [
  CustomerProfileComponent,
  CpProfileSettingsComponent,
  ItemShopSearchComponent,
  ItemSearchResultComponent,
  CustomerAccountComponent,
  SearchResultComponent,
  ItemSearchResultViewComponent
];

@NgModule({
  declarations: [
    AppCustomerComponent,
    components
  ],
  exports: [components],
  imports: [
    SharedModule,
    AppCustomerRoutingModule
  ]
})
export class AppCustomerModule {
}
