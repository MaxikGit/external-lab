import {Injectable} from '@angular/core';
import {Item} from "./item";
import {BehaviorSubject, Observable} from "rxjs";
import {ItemsDummydataService} from "./items-dummydata.service";

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  private items: Item[] = [];
  private items$ = new BehaviorSubject<Item[]>(this.items);

  constructor(private itemsData: ItemsDummydataService) {
  }

  getItems(): Observable<Item[]> {
    return this.items$;
  }

  loadItems(page: number, itemsPerPage: number) {
    this.itemsData.getItems(page, itemsPerPage).subscribe(data => {
      this.items.push(...data);
      this.updateStream();
    });
  }

  getItemById(index: number): Item {
    return this.itemsData.getItemById(index);
  }

  addItem(item: Item): boolean {
    let itemSaved = this.itemsData.addItem(item);
    if (itemSaved) {
      this.items.push(itemSaved);
      this.updateStream();
      return true;
    }
    return false;
  }

  private updateStream() {
    this.items$.next(this.items);
  }
}
