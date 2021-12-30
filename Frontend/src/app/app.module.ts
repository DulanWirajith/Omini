import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './view/header/header.component';
import {BusinessRegisterComponent} from './shop-profile/view/business-register/business-register.component';
import {BrStep1Component} from './shop-profile/view/business-register/br-step1/br-step1.component';
import {BrStep2Component} from './shop-profile/view/business-register/br-step2/br-step2.component';
import {BrStep3Component} from './shop-profile/view/business-register/br-step3/br-step3.component';
import {BrStep4Component} from './shop-profile/view/business-register/br-step4/br-step4.component';
import {BrStep5Component} from './shop-profile/view/business-register/br-step5/br-step5.component';
import {BrStep6Component} from './shop-profile/view/business-register/br-step6/br-step6.component';
import {NgSelectModule} from "@ng-select/ng-select";
// import filepond module
import {FilePondModule, registerPlugin} from 'ngx-filepond';

import * as FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import * as FilepondPluginImageEdit from 'filepond-plugin-image-edit';
import * as FilepondPluginImagePreview from 'filepond-plugin-image-preview';

registerPlugin(FilePondPluginFileValidateType, FilepondPluginImageEdit, FilepondPluginImagePreview);

import {ShopProfileModule} from "./shop-profile/shop-profile.module";
import {CustomerProfileModule} from "./customer-profile/customer-profile.module";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    ShopProfileModule,
    CustomerProfileModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
