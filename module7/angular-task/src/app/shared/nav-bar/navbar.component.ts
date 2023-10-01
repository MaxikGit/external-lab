import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../../items/cart.service";
import {map, Observable, Subscription} from "rxjs";
import {FavouriteService} from "../../items/favourite.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

  cartBadgeHidden = true;
  isFavouritePressed = false;
  isFavouriteBlocked = true;
  cartItems$?: Observable<number>;
  @Input() logo = "Logo";
  private cartSubscription?: Subscription;
  private favouriteSubscription?: Subscription;
  private favouriteBlockedSubscription?: Subscription;

  constructor(private cartService: CartService, private favouriteService: FavouriteService) {
  }

  ngOnInit(): void {
    this.cartItems$ = this.cartService.items$
      .pipe(
        map(data => Array.from(data.values())
          .reduce((acc, el) => acc + el, 0))
      );
    this.cartSubscription = this.cartItems$.subscribe(number => {
        this.cartBadgeHidden = number === 0;
      }
    );

    this.favouriteSubscription = this.favouriteService.isFavouriteShown$
      .subscribe(isFavouriteShown => {
        this.isFavouritePressed = isFavouriteShown;
      });

    this.favouriteBlockedSubscription = this.favouriteService.isFavouriteBlocked$
      .subscribe(isFavouriteBlocked => {
        this.isFavouriteBlocked = isFavouriteBlocked;
      });
  }

  toggleFavourite() {
    if (this.favouriteService.favouriteItems.length > 0)
      this.isFavouritePressed = this.favouriteService.toggleShowFavourite();
  }

  ngOnDestroy(): void {
    if (this.cartSubscription)
      this.cartSubscription.unsubscribe();

    if (this.favouriteSubscription)
      this.favouriteSubscription.unsubscribe();

    if (this.favouriteBlockedSubscription)
      this.favouriteBlockedSubscription.unsubscribe();
  }
}
