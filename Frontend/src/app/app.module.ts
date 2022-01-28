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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
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
