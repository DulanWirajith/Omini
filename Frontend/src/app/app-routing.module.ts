import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: 'shop',
    loadChildren: () => import('./shop-profile/shop-profile.module').then(m => m.ShopProfileModule)
  },
  {
    path: 'customer',
    loadChildren: () => import('./customer-profile/customer-profile.module').then(m => m.CustomerProfileModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
