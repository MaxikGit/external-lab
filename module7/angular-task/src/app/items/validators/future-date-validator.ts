import {AbstractControl} from "@angular/forms";

export class FutureDateValidator {

  static validateDateIsFuture(control: AbstractControl) {
    const value = new Date(control.value);
    if (value <= new Date()) {
      return {invalidFutureDate: true};
    }
    return null;
  }
}
