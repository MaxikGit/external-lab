import {FormGroup} from "@angular/forms";

export class PasswordValidator {

  static validatePassword(control: FormGroup) {
    const password: any = control.get("password")?.value;
    const rePassword: any = control.get("rePassword")?.value; //type any to make possible subtraction
    if (password === rePassword) {
      return null;
    } else {
      control.get("rePassword")?.setErrors({invalidPass: true});
      return {invalidPass: true};
    }
  }
}
