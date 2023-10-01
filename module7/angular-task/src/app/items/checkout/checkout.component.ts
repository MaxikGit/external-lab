import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../cart.service";
import {Item} from "../item";
import {Location} from "@angular/common";
import {SnackBarService} from "../../shared/snack-bar.service";
import {Subscription} from "rxjs";
import {AlphaNumericSortService} from "../../shared/alpha-numeric-sort.service";
import {Router} from "@angular/router";
import {BackButtonService} from "../../shared/back-button.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit, OnDestroy {

  tooltipShowDelay = 500; //ms
  total = 0;
  itemsMap: Map<Item, number> = new Map();
  private cartSubscription?: Subscription;

  constructor(private location: Location, private router: Router, private nameSortService: AlphaNumericSortService,
              private cartService: CartService, private snackBar: SnackBarService, private backButton: BackButtonService) {
  }

  ngOnInit(): void {
    this.cartSubscription = this.cartService.items$.subscribe(data => {
        console.log(data.size);
        if (data.size === 0) {
          this.router.navigate(['']);
        } else {
          this.itemsMap = data;
          this.total = Array.from(data.entries())
            .map(([key, value]) => key.price * value)
            .reduce((acc, el) => acc + el, 0);
        }
      }
    )
  }

  onBack() {
    this.backButton.back();
  }

  onCheckout() {
    this.cartService.clearCart();
    this.router.navigate(['']);
    this.snackBar.openSuccessSnackBar("Order was successfully placed")
  }

  removeItem(item: Item) {
    this.cartService.removeItem(item);
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      console.log('unsubscribed cart items')
      this.cartSubscription.unsubscribe();
    }
  }
}
