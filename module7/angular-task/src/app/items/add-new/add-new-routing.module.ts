import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddNewComponent} from './add-new.component';
import {formDeactivateGuard} from "../../guards/can-deactivate-form.guard";

const routes: Routes = [{path: '', component: AddNewComponent, canDeactivate: [formDeactivateGuard]}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddNewRoutingModule {
}
