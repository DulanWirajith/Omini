import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: 'shop', loadChildren: () => import('./app-shop/app-shop.module').then(m => m.AppShopModule)},
  {path: 'customer', loadChildren: () => import('./app-customer/app-customer.module').then(m => m.AppCustomerModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
