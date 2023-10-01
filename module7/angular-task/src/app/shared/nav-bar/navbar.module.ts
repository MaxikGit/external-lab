import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar.component';
import {RouterLink} from "@angular/router";
import {CollapseButtonRowComponent} from './collapse-button-row/collapse-button-row.component';
import {SearchBoxComponent} from './search-box/search-box.component';
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import { CategoryFilterPipe } from './search-box/category-filter.pipe';
import {MatBadgeModule} from "@angular/material/badge";
import {MatTooltipModule} from "@angular/material/tooltip";


@NgModule({
  declarations: [
    NavbarComponent,
    CollapseButtonRowComponent,
    SearchBoxComponent,
    CategoryFilterPipe
  ],
  exports: [
    NavbarComponent
  ],
  imports: [
    CommonModule,
    RouterLink,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatBadgeModule,
    MatTooltipModule

  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}
  ]
})

export class NavbarModule {
}
