import {Component, Input, OnInit} from '@angular/core';
import {Item} from "../../item";
import {CartService} from "../../cart.service";
import {Subscription} from "rxjs";
import {FavouriteService} from "../../favourite.service";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  addToCartPressed = 0;
  isFavourite = false;
  @Input() item!: Item;
  private cartSubscription?: Subscription;

  constructor(private cartService: CartService, private favouriteService: FavouriteService) {
  }

  addToCart(item: Item) {
    this.cartService.addToCart(item);
  }

  ngOnInit(): void {
    this.cartSubscription = this.cartService.items$
      .subscribe(data => {
        this.addToCartPressed = data.get(this.item) ?? 0;
      });
    this.isFavourite = this.favouriteService.favouriteItems.includes(this.item);
  }

  toggleFavourite() {
    this.isFavourite = this.favouriteService.toggleFavouriteItem(this.item);
  }
}
