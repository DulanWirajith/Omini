import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './view/header/header.component';
import { BusinessRegisterComponent } from './view/business-register/business-register.component';
import { BrStep1Component } from './view/business-register/br-step1/br-step1.component';
import { BrStep2Component } from './view/business-register/br-step2/br-step2.component';
import { BrStep3Component } from './view/business-register/br-step3/br-step3.component';
import { BrStep4Component } from './view/business-register/br-step4/br-step4.component';
import { BrStep5Component } from './view/business-register/br-step5/br-step5.component';
import { BrStep6Component } from './view/business-register/br-step6/br-step6.component';
import {NgSelectModule} from "@ng-select/ng-select";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BusinessRegisterComponent,
    BrStep1Component,
    BrStep2Component,
    BrStep3Component,
    BrStep4Component,
    BrStep5Component,
    BrStep6Component
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
