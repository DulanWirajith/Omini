import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./view/login/login.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'shop', loadChildren: () => import('./app-shop/app-shop.module').then(m => m.AppShopModule)},
  {path: 'customer', loadChildren: () => import('./app-customer/app-customer.module').then(m => m.AppCustomerModule)},
  {
    path: '',
    redirectTo: '/customer/header/item_shop_search',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
