import {Injectable} from '@angular/core';
import {User} from "../user";
import {UsersService} from "../users.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  isLoggedIn = false;
  isAdmin = false;

  constructor(private userService: UsersService) {
  }

  login(loginUser: User) {
    let user = this.userService.getUserByEmail(loginUser.email);
    if (user && loginUser.password === user.password){
      this.isLoggedIn = true;
      this.isAdmin = this.userService.isAdmin(user);
      return user;
    }
    return undefined;  }
}
