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
import {ShopViewComponent} from "../view/shop-view/shop-view.component";
import {BaReviewsComponent} from "./view/business-account/ba-reviews/ba-reviews.component";

const routes: Routes = [
  // {
  //   path: '/shop',
  //   redirectTo: '/shop/header/business_account/ba_monitor',
  //   pathMatch: 'full'
  // },
  {
    path: 'business_register',
    component: BusinessRegisterComponent,
  },
  {
    path: 'header',
    component: AppShopComponent,
    children: [
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
          },
          {
            path: 'ba_reviews',
            component: BaReviewsComponent,
          }
        ]
      },
      {
        path: 'shop_view',
        component: ShopViewComponent,
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
