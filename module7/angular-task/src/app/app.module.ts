import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {LoginModule} from "./users/login/login.module";
import {NotFoundComponent} from './not-found/not-found.component';
import {SignUpModule} from "./users/sign-up/sign-up.module";
import {HttpClientModule} from "@angular/common/http";
import {AddNewModule} from "./items/add-new/add-new.module";
import {CheckoutModule} from "./items/checkout/checkout.module";
import {SharedModule} from "./shared/shared.module";

@NgModule({
    declarations: [
        AppComponent,
        NotFoundComponent,
    ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    LoginModule,
    SignUpModule,
    HttpClientModule,
    AddNewModule,
    CheckoutModule
  ],
    exports: [
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
