import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './main-page.component';
import {ItemDetailsComponent} from "./item-details/item-details.component";

const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: ':id', component: ItemDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainPageRoutingModule { }
