import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConfirmPasswordDirective} from "../_validator/confirm-password.directive";
import {FormsModule} from "@angular/forms";
import {FilePondModule} from "ngx-filepond";
import {NgSelectModule} from "@ng-select/ng-select";
import {HttpClientModule} from "@angular/common/http";
import {MDBBootstrapModule} from "angular-bootstrap-md";
import {HeaderComponent} from "../view/header/header.component";

const components = [
  HeaderComponent,
  ConfirmPasswordDirective
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
  ],
  declarations: [
    components
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule {
}
