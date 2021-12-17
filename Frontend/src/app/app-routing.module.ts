import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HeaderComponent} from "./view/header/header.component";
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";
import {BusinessAccountComponent} from "./view/business-account/business-account.component";
import {BaManageComponent} from "./view/business-account/ba-manage/ba-manage.component";
import {BaMonitorComponent} from "./view/business-account/ba-monitor/ba-monitor.component";
import {CustomerProfileComponent} from "./view/profile/customer-profile/customer-profile.component";
import {CpProfileSettingsComponent} from "./view/profile/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {ShopProfileComponent} from "./view/profile/shop-profile/shop-profile.component";
import {SpProfileSettingsComponent} from "./view/profile/shop-profile/sp-profile-settings/sp-profile-settings.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'business_account',
    pathMatch: 'full'
  },
  {
    path: 'header',
    component: HeaderComponent,
  },
  {
    path: 'business_register',
    component: BusinessRegisterComponent,
  },
  {
    path: 'business_account',
    component: BusinessAccountComponent,
    children: [
      {
        path: 'ba_monitor',
        component: BaMonitorComponent,
      },
      {
        path: 'ba_manage',
        component: BaManageComponent,
      }
    ]
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
  },
  {
    path: 'shop_profile',
    component: ShopProfileComponent,
    children: [
      {
        path: 'profile_settings',
        component: SpProfileSettingsComponent,
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
