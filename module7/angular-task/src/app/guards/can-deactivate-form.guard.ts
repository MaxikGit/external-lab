import {CanDeactivateFn} from '@angular/router';
import {inject} from "@angular/core";
import {SnackBarService} from "../shared/snack-bar.service";
import {FormInputComponent} from "../form-input-component";

//deactivate route change if page is not pristine
export const formDeactivateGuard: CanDeactivateFn<FormInputComponent> = (route, ) => {
  if (route.canDeactivate()) {
    return true;
  }else{
    inject(SnackBarService).openInfoSnackBar("You have unsaved changes!");
    return false;
  }
};
