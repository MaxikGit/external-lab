import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MainPageRoutingModule} from './main-page-routing.module';
import {MainPageComponent} from './main-page.component';
import {ItemComponent} from './item-element/item.component';
import {ScrollToTopComponent} from './scroll-to-top/scroll-to-top.component';
import {ItemDetailsComponent} from './item-details/item-details.component';
import {MatBadgeModule} from "@angular/material/badge";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
  declarations: [
    MainPageComponent,
    ItemComponent,
    ScrollToTopComponent,
    ItemDetailsComponent
  ],
  imports: [
    CommonModule,
    MainPageRoutingModule,
    MatBadgeModule,
    InfiniteScrollModule,
    MatProgressSpinnerModule
  ],
})

export class MainPageModule {
}
