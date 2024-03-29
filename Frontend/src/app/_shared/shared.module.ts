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
import {LightboxModule} from 'ngx-lightbox';
import {ItemPackageDetailViewComponent} from "../view/item-package-detail-view/item-package-detail-view.component";
import {NotifierModule, NotifierOptions} from "angular-notifier";
import {ShopViewComponent} from "../view/shop-view/shop-view.component";
import {ShopCartComponent} from "../app-customer/view/shop-cart/shop-cart.component";

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: "middle",
      distance: 5
    },
    vertical: {
      position: "top",
      distance: 10,
      gap: 10
    }
  },
  theme: "material",
  behaviour: {
    autoHide: 5000,
    onClick: false,
    onMouseover: "pauseAutoHide",
    showDismissButton: false,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: "slide",
      speed: 300,
      easing: "ease"
    },
    hide: {
      preset: "fade",
      speed: 300,
      easing: "ease",
      offset: 50
    },
    shift: {
      speed: 300,
      easing: "ease"
    },
    overlap: 150
  }
};

const components = [
  HeaderComponent,
  ConfirmPasswordDirective,
  ItemPackageDetailViewComponent,
  ShopViewComponent,
  ShopCartComponent
  // NotifierModule.withConfig(customNotifierOptions)
]

let notifierModule: any = NotifierModule.withConfig(customNotifierOptions)

@NgModule({
  exports: [
    components,
    CommonModule,
    FormsModule,
    FilePondModule,
    NgSelectModule,
    HttpClientModule,
    MDBBootstrapModule,
    LightboxModule,
    notifierModule
  ],
  declarations: [
    components
  ],
  imports: [
    CommonModule,
    RouterModule,
    MDBBootstrapModule,
    FormsModule,
    notifierModule,
    NgSelectModule
  ]
})
export class SharedModule {
}
