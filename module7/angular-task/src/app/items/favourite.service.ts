import {Injectable} from '@angular/core';
import {Item} from "./item";
import {BehaviorSubject, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FavouriteService {

  favouriteItems: Item[] = [];
  isFavouriteShown$ = new BehaviorSubject<boolean>(false);
  isFavouriteBlocked$ = new BehaviorSubject<boolean>(this.favouriteItems.length === 0);

  constructor() {
  }

  getItems(): Observable<Item[]>{
    return of(this.favouriteItems);
  }

  /**
   * method to turn favourite item. Returns true if favourite is "On", false otherwise
   * @param item
   */
  toggleFavouriteItem(item: Item): boolean {
    let index = this.favouriteItems.indexOf(item);
    if (index < 0) {
      this.favouriteItems.push(item);
    }else {
      this.favouriteItems.splice(index, 1);
    }
    this.updateStreams();
    return index < 0;
  }

  toggleShowFavourite(){
    this.isFavouriteShown$.next(!this.isFavouriteBlocked$.value && !this.isFavouriteShown$.value);
    return this.isFavouriteShown$.value;
  }

  private updateStreams() {
    if (this.favouriteItems.length === 0) {
      this.isFavouriteShown$.next(false);
      this.isFavouriteBlocked$.next(true);
    }else {
      this.isFavouriteBlocked$.next(false);
    }
  }
}
