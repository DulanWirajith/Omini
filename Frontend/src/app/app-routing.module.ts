import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HeaderComponent} from "./view/header/header.component";
import {BusinessRegisterComponent} from "./view/business-register/business-register.component";

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
