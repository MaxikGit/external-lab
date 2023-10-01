import {CanActivateFn, CanMatchFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {LoginService} from "../login/login.service";
import {SnackBarService} from "../../shared/snack-bar.service";

export const loginMatchGuard: CanMatchFn = (route, state) => {
  return checkLogin('user');
};

export const loginActivateGuard: CanActivateFn = (route, state) => {
  return checkLogin('user');
};

export const adminMatchGuard: CanMatchFn = (route, state) => {
  return checkLogin('admin');
};

export const adminActivateGuard: CanActivateFn = () => {
  return checkLogin('admin');
};

const checkLogin = (loginType: string) => {
  let isLogged;
  if (loginType === 'admin') {
    isLogged = inject(LoginService).isAdmin;
  } else {
    isLogged = inject(LoginService).isLoggedIn;
  }
  if (isLogged) {
    return true;
  }
  inject(SnackBarService).openInfoSnackBar("Access granted only for logged in users");
  return inject(Router).navigate(["/login"]);
};
