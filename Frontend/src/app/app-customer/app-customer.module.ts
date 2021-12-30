import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppCustomerRoutingModule } from './app-customer-routing.module';
import { AppCustomerComponent } from './app-customer.component';
import {CpProfileSettingsComponent} from "./view/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {SharedModule} from "../_shared/shared.module";
import {CustomerProfileComponent} from "./view/customer-profile/customer-profile.component";

const components = [
  CustomerProfileComponent,
  CpProfileSettingsComponent
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
export class AppCustomerModule { }
