import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HeaderComponent} from "../view/header/header.component";
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";
import {BusinessAccountComponent} from "./view/business-account/business-account.component";
import {BaMonitorComponent} from "./view/business-account/ba-monitor/ba-monitor.component";
import {BaManageComponent} from "./view/business-account/ba-manage/ba-manage.component";
import {CustomerProfileComponent} from "../customer-profile/view/customer-profile/customer-profile.component";
import {CpProfileSettingsComponent} from "../customer-profile/view/customer-profile/cp-profile-settings/cp-profile-settings.component";
import {ShopProfileComponent} from "./view/shop-profile/shop-profile.component";
import {SpProfileSettingsComponent} from "./view/shop-profile/sp-profile-settings/sp-profile-settings.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/shop/business_account',
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
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopProfileRoutingModule { }
