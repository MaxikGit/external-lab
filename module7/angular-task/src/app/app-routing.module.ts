import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./users/login/login.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {adminActivateGuard, adminMatchGuard, loginActivateGuard, loginMatchGuard} from "./users/guards/loginMatchGuard";
import {canActivateCartGuard, canMatchCart} from "./items/guards/can-activate-cart.guard";

const routes: Routes = [
  {path: "", redirectTo: "items", pathMatch: "full"},
  {path: 'login', component: LoginComponent},
  {path: 'items', loadChildren: () => import('./items/main-page/main-page.module').then(m => m.MainPageModule)},
  {path: 'sign_up', loadChildren: () => import('./users/sign-up/sign-up.module').then(m => m.SignUpModule)},
  {
    path: 'add_new',
    loadChildren: () => import('./items/add-new/add-new.module').then(m => m.AddNewModule),
    canMatch: [adminMatchGuard], canActivate: [adminActivateGuard]
  },
  {
    path: 'checkout', loadChildren: () => import('./items/checkout/checkout.module').then(m => m.CheckoutModule),
    canMatch: [loginMatchGuard, canMatchCart], canActivate: [loginActivateGuard, canActivateCartGuard]
  },
  {path: "**", component: NotFoundComponent,}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
