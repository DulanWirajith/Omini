import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppShopRoutingModule } from './app-shop-routing.module';
import { AppShopComponent } from './app-shop.component';
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";
import {BrStep1Component} from "./view/business-register/br-step1/br-step1.component";
import {BrStep2Component} from "./view/business-register/br-step2/br-step2.component";
import {BrStep3Component} from "./view/business-register/br-step3/br-step3.component";
import {BrStep4Component} from "./view/business-register/br-step4/br-step4.component";
import {BrStep5Component} from "./view/business-register/br-step5/br-step5.component";
import {BrStep6Component} from "./view/business-register/br-step6/br-step6.component";
import {BusinessAccountComponent} from "./view/business-account/business-account.component";
import {BaManageComponent} from "./view/business-account/ba-manage/ba-manage.component";
import {BaMonitorComponent} from "./view/business-account/ba-monitor/ba-monitor.component";
import {BaManageItemComponent} from "./view/business-account/ba-manage/ba-manage-item/ba-manage-item.component";
import {BaManageCategoryComponent} from "./view/business-account/ba-manage/ba-manage-category/ba-manage-category.component";
import {BaManagePackageComponent} from "./view/business-account/ba-manage/ba-manage-package/ba-manage-package.component";
import {BaManageItemViewComponent} from "./view/business-account/ba-manage/ba-manage-item/ba-manage-item-view/ba-manage-item-view.component";
import {BaManageItemEditComponent} from "./view/business-account/ba-manage/ba-manage-item/ba-manage-item-edit/ba-manage-item-edit.component";
import {SharedModule} from "../_shared/shared.module";
import {BaManagePackageEditComponent} from "./view/business-account/ba-manage/ba-manage-package/ba-manage-package-edit/ba-manage-package-edit.component";
import {BaProfileComponent} from "./view/business-account/ba-profile/ba-profile.component";
import {BaOrderComponent} from "./view/business-account/ba-order/ba-order.component";
import {BaOrderViewComponent} from "./view/business-account/ba-order/ba-order-view/ba-order-view.component";

const components = [
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
  BaManageItemComponent,
  BaManageCategoryComponent,
  BaManagePackageComponent,
  BaManageItemViewComponent,
  BaManageItemEditComponent,
  BaManagePackageEditComponent,
  BaProfileComponent,
  BaOrderComponent,
  BaOrderViewComponent
];

@NgModule({
  declarations: [
    AppShopComponent,
    components
  ],
  exports: [components],
  imports: [
    SharedModule,
    AppShopRoutingModule
  ]
})
export class AppShopModule { }
