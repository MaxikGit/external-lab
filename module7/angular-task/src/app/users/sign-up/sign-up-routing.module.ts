import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './sign-up.component';
import {formDeactivateGuard} from "../../guards/can-deactivate-form.guard";

const routes: Routes = [{ path: '', component: SignUpComponent, canDeactivate: [formDeactivateGuard] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SignUpRoutingModule { }
