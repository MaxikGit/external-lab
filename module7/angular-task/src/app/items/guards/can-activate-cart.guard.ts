import {CanActivateFn, CanMatchFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {CartService} from "../cart.service";

export const canActivateCartGuard: CanActivateFn = (route, state) => {
  return checkCart(inject(CartService));
};

export const canMatchCart: CanMatchFn = (route, state) => {
  return checkCart(inject(CartService));
}

const checkCart = (cartService: CartService) => {
  if (cartService.getItems().size > 0) {
    return true;
  }
  return inject(Router).navigate(["/"]);
};
