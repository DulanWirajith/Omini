import {NgModule} from '@angular/core';

import {CustomerProfileRoutingModule} from './customer-profile-routing.module';
import {CustomerProfileComponent} from "./view/customer-profile/customer-profile.component";
import {CpProfileSettingsComponent} from "./view/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {SharedModule} from "../_shared/shared.module";

const components = [
  CustomerProfileComponent,
  CpProfileSettingsComponent
]

@NgModule({
  declarations: [
    components
  ],
  exports: [
    components
  ],
  imports: [
    SharedModule,
    CustomerProfileRoutingModule
  ]
})
export class CustomerProfileModule {
}
