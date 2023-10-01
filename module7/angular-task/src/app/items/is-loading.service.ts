import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class IsLoadingService {

  isLoading$ = new BehaviorSubject(false);

  startLoading(){
    this.isLoading$.next(true);
  }

  finishLoading(){
    this.isLoading$.next(false);
  }

}
