import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HeaderComponent} from "../view/header/header.component";
import {CustomerProfileComponent} from "./view/customer-profile/customer-profile.component";
import {CpProfileSettingsComponent} from "./view/customer-profile/cp-profile-settings/cp-profile-settings.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'profile_settings',
    pathMatch: 'full'
  },
  {
    path: 'header',
    component: HeaderComponent,
  },
  {
    path: 'customer_profile',
    component: CustomerProfileComponent,
    children: [
      {
        path: 'profile_settings',
        component: CpProfileSettingsComponent,
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerProfileRoutingModule {
}
