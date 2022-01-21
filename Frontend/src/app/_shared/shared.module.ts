import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConfirmPasswordDirective} from "../_validator/confirm-password.directive";
import {FormsModule} from "@angular/forms";
import {FilePondModule} from "ngx-filepond";
import {NgSelectModule} from "@ng-select/ng-select";
import {HttpClientModule} from "@angular/common/http";
import {MDBBootstrapModule} from "angular-bootstrap-md";
import {HeaderComponent} from "../view/header/header.component";
import {RouterModule} from "@angular/router";
import { LightboxModule } from 'ngx-lightbox';
import {LoginComponent} from "../view/login/login.component";
import {ItemDetailViewComponent} from "../view/item-detail-view/item-detail-view.component";
import {ItemPackageDetailViewComponent} from "../view/item-package-detail-view/item-package-detail-view.component";

const components = [
  HeaderComponent,
  ConfirmPasswordDirective,
  ItemDetailViewComponent,
  ItemPackageDetailViewComponent
  // LoginComponent
]

@NgModule({
  exports: [
    components,
    CommonModule,
    FormsModule,
    FilePondModule,
    NgSelectModule,
    HttpClientModule,
    MDBBootstrapModule,
    LightboxModule
  ],
  declarations: [
    components
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class SharedModule {
}
