import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppShopComponent} from './app-shop.component';
import {HeaderComponent} from "../view/header/header.component";
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";
import {BusinessAccountComponent} from "./view/business-account/business-account.component";
import {BaMonitorComponent} from "./view/business-account/ba-monitor/ba-monitor.component";
import {BaManageComponent} from "./view/business-account/ba-manage/ba-manage.component";
import {BaOrderComponent} from "./view/business-account/ba-order/ba-order.component";
import {BaProfileComponent} from "./view/business-account/ba-profile/ba-profile.component";

const routes: Routes = [
  // {
  //   path: '',
  //   redirectTo: '/shop/business_account',
  //   pathMatch: 'full'
  // },
  {
    path: 'header',
    component: AppShopComponent,
    children: [
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
          },
          {
            path: 'ba_order',
            component: BaOrderComponent,
          },
          {
            path: 'ba_order_history',
            component: BaManageComponent,
          },
          {
            path: 'ba_profile',
            component: BaProfileComponent,
          }
        ]
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppShopRoutingModule {
}
