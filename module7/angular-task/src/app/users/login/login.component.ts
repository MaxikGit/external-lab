import {Component, OnDestroy, OnInit, Output} from '@angular/core';
import {LoginService} from "./login.service";
import {User} from "../user";
import {Router} from "@angular/router";
import {SnackBarService} from "../../shared/snack-bar.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  login = "";
  password = "";

  @Output() loggedUserName?: string;

  constructor(private loginService: LoginService, private route: Router,
              private _snackBar: SnackBarService) {
  }

  checkLogin() {
    let userName = this.loginService.login(this.makeUser())?.name;
    if (userName){
      this._snackBar.openSuccessSnackBar(`Welcome ${userName}`);
      this.loggedUserName = userName;
      this.route.navigate(["/"]);
    }else{
      this._snackBar.openInfoSnackBar("Bad credentials");
    }
  }

  private makeUser(): User {
    return {
      login: "",
      email: this.login,
      password: this.password
    };
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
  }

}
