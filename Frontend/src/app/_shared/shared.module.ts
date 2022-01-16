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

const components = [
  HeaderComponent,
  ConfirmPasswordDirective,
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
