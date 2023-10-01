import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AddNewRoutingModule} from './add-new-routing.module';
import {AddNewComponent} from './add-new.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    AddNewComponent,
  ],
  imports: [
    CommonModule,
    AddNewRoutingModule,
    ReactiveFormsModule,
    MatSelectModule,
    SharedModule
  ],
  providers: []
})
export class AddNewModule {
}
