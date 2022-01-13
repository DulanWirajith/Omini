import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AppCustomerRoutingModule} from './app-customer-routing.module';
import {AppCustomerComponent} from './app-customer.component';
import {SharedModule} from "../_shared/shared.module";
import {ItemShopSearchComponent} from "./view/item-shop-search/item-shop-search.component";
import {CustomerAccountComponent} from "./view/customer-account/customer-account.component";
import {SearchResultComponent} from "./view/search-result/search-result.component";
import {ItemSearchResultViewComponent} from "./view/search-result/item-package-search-result/item-search-result-view/item-search-result-view.component";
import {ItemPackageSearchResultComponent} from "./view/search-result/item-package-search-result/item-package-search-result.component";
import {ItemSearchResultDetailViewComponent} from "./view/search-result/item-package-search-result/item-search-result-detail-view/item-search-result-detail-view.component";
import {ShopCartComponent} from "./view/shop-cart/shop-cart.component";
import {ShopCartService} from "./_service/shop-cart.service";
import {CProfileComponent} from "./view/customer-account/c-profile/c-profile.component";
import {COrderComponent} from "./view/customer-account/c-order/c-order.component";

const components = [
  ItemShopSearchComponent,
  CustomerAccountComponent,
  SearchResultComponent,
  ItemSearchResultViewComponent,
  ItemPackageSearchResultComponent,
  ItemSearchResultDetailViewComponent,
  ShopCartComponent,
  CProfileComponent,
  COrderComponent
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
  ],
  providers: [
    // ShopCartService
  ]
})
export class AppCustomerModule {
  // static forRoot(): ModuleWithProviders<AppCustomerModule> {
  //   return {
  //     ngModule: AppCustomerModule,
  //     providers: [ShopCartService]
  //   }
  // }
}
