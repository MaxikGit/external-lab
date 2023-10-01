import {Injectable} from '@angular/core';
import {ItemsService} from "../items.service";
import {FavouriteService} from "../favourite.service";
import {Observable, switchMap} from "rxjs";
import {Item} from "../item";

@Injectable({
  providedIn: 'root'
})
export class ItemSourceService {

  private currentPage = 1;
  private itemsPerPage = 8;

  constructor(private itemService: ItemsService, private favoriteService: FavouriteService) {
    this.loadData();
  }

  getItems(): Observable<Item[]> {
    return this.favoriteService.isFavouriteShown$.pipe(
      switchMap(showFavourite => {
          if (showFavourite) {
            return this.favoriteService.getItems();
          } else {
            return this.itemService.getItems();
          }
        }
      )
    );
  }

  loadData() {
    console.log(this.currentPage);
    if (!this.favoriteService.isFavouriteShown$.value) {
      this.itemService.loadItems(this.currentPage++, this.itemsPerPage);
    }
    console.log(this.currentPage);
    return this.currentPage;
  }
}
