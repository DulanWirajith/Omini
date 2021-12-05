import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HeaderComponent} from "./view/header/header.component";
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";
import {BusinessAccountComponent} from "./view/business-account/business-account.component";
import {BaManageComponent} from "./view/business-account/ba-manage/ba-manage.component";
import {BaMonitorComponent} from "./view/business-account/ba-monitor/ba-monitor.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'business_register',
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
