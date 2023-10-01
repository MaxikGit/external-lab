import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(private _snackBar: MatSnackBar) { }

  openInfoSnackBar(message: string) {
    this.openSnackBar(message, ["light-grey-snackbar", "text-center"]);
  }
  openSuccessSnackBar(message: string) {
    this.openSnackBar(message, ["light-green-snackbar", "text-center"]);
  }
  private openSnackBar(message: string, css: string[]) {
    this._snackBar.open(message, "", {
      duration: 3500,
      panelClass: css
    })
  }
}
