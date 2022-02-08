import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

// import filepond module
import {FilePondModule, registerPlugin} from 'ngx-filepond';

import * as FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import * as FilepondPluginImageEdit from 'filepond-plugin-image-edit';
import * as FilepondPluginImagePreview from 'filepond-plugin-image-preview';

registerPlugin(FilePondPluginFileValidateType, FilepondPluginImageEdit, FilepondPluginImagePreview);

import {RouterModule} from "@angular/router";
import {SharedModule} from "./_shared/shared.module";
import { LoginComponent } from './view/login/login.component';
import { CFavouriteComponent } from './app-customer/view/customer-account/c-favourite/c-favourite.component';
import { ShopSearchResultComponent } from './app-customer/view/search-result/shop-search-result/shop-search-result.component';
import { ShopViewComponent } from './view/shop-view/shop-view.component';
import { CFollowingComponent } from './app-customer/view/customer-account/c-following/c-following.component';
import { BaReviewsComponent } from './app-shop/view/business-account/ba-reviews/ba-reviews.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,



    // TimeAgoPipe
    // HeaderComponent
  ],
  imports: [
    BrowserModule,
    // CommonModule,
    AppRoutingModule,
    // HttpClientModule,
    RouterModule,
    SharedModule
    // AppCustomerModule.forRoot()
    // NgbModule
    // AppShopModule,
    // AppCustomerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
