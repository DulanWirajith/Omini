import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './view/header/header.component';
import {BusinessRegisterComponent} from './app-shop/view/business-register/business-register.component';
import {BrStep1Component} from './app-shop/view/business-register/br-step1/br-step1.component';
import {BrStep2Component} from './app-shop/view/business-register/br-step2/br-step2.component';
import {BrStep3Component} from './app-shop/view/business-register/br-step3/br-step3.component';
import {BrStep4Component} from './app-shop/view/business-register/br-step4/br-step4.component';
import {BrStep5Component} from './app-shop/view/business-register/br-step5/br-step5.component';
import {BrStep6Component} from './app-shop/view/business-register/br-step6/br-step6.component';
import {NgSelectModule} from "@ng-select/ng-select";
import { LightboxModule } from 'ngx-lightbox';

// import filepond module
import {FilePondModule, registerPlugin} from 'ngx-filepond';

import * as FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import * as FilepondPluginImageEdit from 'filepond-plugin-image-edit';
import * as FilepondPluginImagePreview from 'filepond-plugin-image-preview';

registerPlugin(FilePondPluginFileValidateType, FilepondPluginImageEdit, FilepondPluginImagePreview);

import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import { BaProfileComponent } from './app-shop/view/business-account/ba-profile/ba-profile.component';
import { BaOrderHistoryComponent } from './app-shop/view/business-account/ba-order-history/ba-order-history.component';
import { BaOrderComponent } from './app-shop/view/business-account/ba-order/ba-order.component';
import { ItemShopSearchComponent } from './app-customer/view/item-shop-search/item-shop-search.component';
import { CustomerAccountComponent } from './app-customer/view/customer-account/customer-account.component';
import { SearchResultComponent } from './app-customer/view/search-result/search-result.component';
import {RouterModule} from "@angular/router";
import { ItemSearchResultViewComponent } from './app-customer/view/search-result/item-package-search-result/item-search-result-view/item-search-result-view.component';
import { ItemPackageSearchResultComponent } from './app-customer/view/search-result/item-package-search-result/item-package-search-result.component';
import { ItemSearchResultDetailViewComponent } from './app-customer/view/search-result/item-package-search-result/item-search-result-detail-view/item-search-result-detail-view.component';
import { CartComponent } from './app-customer/view/search-result/cart/cart.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule
    // NgbModule
    // AppShopModule,
    // AppCustomerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
