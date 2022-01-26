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
import {ShopCartComponent} from "./view/shop-cart/shop-cart.component";
import {ShopCartService} from "./_service/shop-cart.service";
import {CProfileComponent} from "./view/customer-account/c-profile/c-profile.component";
import {COrderComponent} from "./view/customer-account/c-order/c-order.component";
import {CustomerRegisterComponent} from "./view/customer-register/customer-register.component";
import {CrStep1Component} from "./view/customer-register/cr-step1/cr-step1.component";
import {CrStep2Component} from "./view/customer-register/cr-step2/cr-step2.component";
import {CrStep3Component} from "./view/customer-register/cr-step3/cr-step3.component";
import {CrStep4Component} from "./view/customer-register/cr-step4/cr-step4.component";
import {CrStep5Component} from "./view/customer-register/cr-step5/cr-step5.component";
import {COrderViewComponent} from "./view/customer-account/c-order/c-order-view/c-order-view.component";
import {NotifierModule, NotifierOptions} from "angular-notifier";

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: "middle",
      distance: 5
    },
    vertical: {
      position: "top",
      distance: 10,
      gap: 10
    }
  },
  theme: "material",
  behaviour: {
    autoHide: 5000,
    onClick: false,
    onMouseover: "pauseAutoHide",
    showDismissButton: false,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: "slide",
      speed: 300,
      easing: "ease"
    },
    hide: {
      preset: "fade",
      speed: 300,
      easing: "ease",
      offset: 50
    },
    shift: {
      speed: 300,
      easing: "ease"
    },
    overlap: 150
  }
};

const components = [
  ItemShopSearchComponent,
  CustomerAccountComponent,
  SearchResultComponent,
  ItemSearchResultViewComponent,
  ItemPackageSearchResultComponent,
  ShopCartComponent,
  CProfileComponent,
  COrderComponent,
  CustomerRegisterComponent,
  CrStep1Component,
  CrStep2Component,
  CrStep3Component,
  CrStep4Component,
  CrStep5Component,
  COrderViewComponent
];

@NgModule({
  declarations: [
    AppCustomerComponent,
    components
  ],
  exports: [components],
  imports: [
    SharedModule,
    AppCustomerRoutingModule,
    // NotifierModule.withConfig(customNotifierOptions)
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
