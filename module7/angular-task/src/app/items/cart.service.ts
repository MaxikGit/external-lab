import {Injectable} from '@angular/core';
import {Item} from "./item";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  // private items: Item[];
  private readonly itemsMap: Map<Item, number>;
  items$: BehaviorSubject<Map<Item, number>>;

  constructor() {
    // this.items = [];
    this.itemsMap = new Map<Item, number>();
    this.items$ = new BehaviorSubject(this.itemsMap);
  }

  addToCart(product: Item) {
    let itemsNum = this.itemsMap.get(product);
    if (itemsNum) {
      this.itemsMap.set(product, itemsNum + 1);
    } else {
      this.itemsMap.set(product, 1);
    }
    this.updateStream();
  }

  getItems(): Map<Item, number> {
    return this.itemsMap;
  }

  removeItem(product: Item) {
    console.log(`item with index ${product.id}`);
    let itemsNum = this.itemsMap.get(product) ?? 0;
    if (itemsNum > 1) {
      this.itemsMap.set(product, itemsNum - 1);
    } else {
      this.itemsMap.delete(product);
    }
    this.updateStream();
  }

  clearCart(): void {
    this.itemsMap.clear();
    this.updateStream();
  }

  private updateStream() {
    this.items$.next(this.itemsMap);
  }
}
