import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './view/header/header.component';
import { BusinessRegisterComponent } from './view/business-register/business-register.component';
import { BrStep1Component } from './view/business-register/br-step1/br-step1.component';
import { BrStep2Component } from './view/business-register/br-step2/br-step2.component';
import { BrStep3Component } from './view/business-register/br-step3/br-step3.component';
import { BrStep4Component } from './view/business-register/br-step4/br-step4.component';
import { BrStep5Component } from './view/business-register/br-step5/br-step5.component';
import { BrStep6Component } from './view/business-register/br-step6/br-step6.component';
import {NgSelectModule} from "@ng-select/ng-select";
// import filepond module
import { FilePondModule, registerPlugin } from 'ngx-filepond';

import  * as FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import  * as FilepondPluginImageEdit from 'filepond-plugin-image-edit';
import  * as FilepondPluginImagePreview from 'filepond-plugin-image-preview';
registerPlugin(FilePondPluginFileValidateType,FilepondPluginImageEdit,FilepondPluginImagePreview);

import {FormsModule} from "@angular/forms";
import { BusinessAccountComponent } from './view/business-account/business-account.component';
import { BaManageComponent } from './view/business-account/ba-manage/ba-manage.component';
import { BaMonitorComponent } from './view/business-account/ba-monitor/ba-monitor.component';
import {HttpClientModule} from "@angular/common/http";
import { ConfirmPasswordDirective } from './_validator/confirm-password.directive';
import { ShopProfileComponent } from './view/profile/shop-profile/shop-profile.component';
import { CustomerProfileComponent } from './view/profile/customer-profile/customer-profile.component';
import { BaManageItemComponent } from './view/business-account/ba-manage/ba-manage-item/ba-manage-item.component';
import { BaManageCategoryComponent } from './view/business-account/ba-manage/ba-manage-category/ba-manage-category.component';
import { BaManagePackageComponent } from './view/business-account/ba-manage/ba-manage-package/ba-manage-package.component';
import { CpProfileSettingsComponent } from './view/profile/customer-profile/cp-profile-settings/cp-profile-settings.component';
import { SpProfileSettingsComponent } from './view/profile/shop-profile/sp-profile-settings/sp-profile-settings.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BusinessRegisterComponent,
    BrStep1Component,
    BrStep2Component,
    BrStep3Component,
    BrStep4Component,
    BrStep5Component,
    BrStep6Component,
    BusinessAccountComponent,
    BaManageComponent,
    BaMonitorComponent,
    ConfirmPasswordDirective,
    ShopProfileComponent,
    CustomerProfileComponent,
    BaManageItemComponent,
    BaManageCategoryComponent,
    BaManagePackageComponent,
    CpProfileSettingsComponent,
    SpProfileSettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    FilePondModule,
    NgSelectModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
