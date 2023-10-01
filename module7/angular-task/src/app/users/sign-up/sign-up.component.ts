import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {PasswordValidator} from "./validators/password-validator";
import {Router} from "@angular/router";
import {User} from "../user";
import {UsersService} from "../users.service";
import {SnackBarService} from "../../shared/snack-bar.service";
import {FormInputComponent} from "../../form-input-component";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent extends FormInputComponent implements OnInit  {

  constructor(private formBuilder: FormBuilder, private route: Router,
              private userService: UsersService, private _snackBar: SnackBarService) {
    super();
  }

  onSubmit() {
    let user: User = this.getFormData();
    if (this.userService.addUser(user)) {
      this.inputForm.reset();
      this._snackBar.openSuccessSnackBar("User successfully added, please login");
      this.route.navigate(["login"]);
    } else {
      this._snackBar.openInfoSnackBar("Seems the user already exist");
    }
  }

  ngOnInit(): void {
    this.initFormControls();
  }

  protected initFormControls(): void {
    this.inputForm = this.formBuilder.group({
      login: ["", {validators: [Validators.required]}],
      name: ["", {validators: [Validators.minLength(3)]}],
      password: ["",
        {validators: [Validators.required]},],
      rePassword: ["", {
        updateOn: "change",
        validators: [Validators.required]},],
      email: ["", {validators: [Validators.required, Validators.email]}],
      address: ["", {validators: [Validators.min(10)]}],
    }, {
      updateOn: "blur",
      validators: [PasswordValidator.validatePassword]
    });
  }

  resetForm() {
    if (this.inputForm.pristine) {
      this.route.navigate([""]);
    } else this.inputForm.reset();
  }

  protected getFormData(): any {
    return {
      login: this.inputForm.get('login')!.value,
      name: this.inputForm.get('name')!.value,
      email: this.inputForm.get('email')!.value,
      password: this.inputForm.get('password')!.value,
      address: this.inputForm.get('address')!.value,
    };
  }

}
