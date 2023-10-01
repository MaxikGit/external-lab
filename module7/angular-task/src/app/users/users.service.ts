import {Injectable} from '@angular/core';
import {User} from "./user";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly users: User[];

  constructor() {
    this.users = this.initUsers();
  }

  getUserByEmail(email: string): User {
    return this.users.filter(user => (user.email === email))[0];
  }

  addUser(user: User): boolean {
    if (this.getUserByEmail(user.email)) {
      return false;
    }
    this.users.push(user);
    console.log(this.users);
    return true;
  }

  isAdmin(user: User){
    return user.password === 'admin';
  }

  private initUsers(): User[] {
    return [
      {
        name: "Sasha",
        login: "alex",
        email: "admin@admin.com",
        password: "admin"
      },
      {
        name: "John",
        login: "jonn-y",
        email: "user@user.com",
        password: "user"
      },
    ];
  }
}
