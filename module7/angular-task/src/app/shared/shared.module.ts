import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import {GreyStripComponent} from "./grey-strip/grey-strip.component";
import {NavbarModule} from "./nav-bar/navbar.module";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatBadgeModule} from "@angular/material/badge";
import {MatTooltipModule} from "@angular/material/tooltip";


@NgModule({
  declarations: [
    GreyStripComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
    MatBadgeModule,
    NavbarModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
  exports: [
    CommonModule,
    GreyStripComponent,
    MatBadgeModule,
    NavbarModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
})
export class SharedModule { }
