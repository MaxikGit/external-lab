import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Item} from "../../item";
import {ItemsService} from "../../items.service";
import {CartService} from "../../cart.service";

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {

  item!: Item;

  constructor(private activatedRoute: ActivatedRoute, private itemService: ItemsService,
              private cartService: CartService) {
  }

  timeLeftToBuy() {
    let now = new Date();
    let expiration = new Date(this.item.createDate);
    expiration.setDate(expiration.getDate() + this.item.duration);
    let diff = expiration.getTime() - now.getTime();
    if (diff <= 0) {
      return "expired";
    } else {
      let hours = Math.floor(diff % (1000 * 60 * 60 * 24) / (1000 * 60 * 60));
      let days = Math.floor(diff / (1000 * 60 * 60 * 24));
      return `${days} days ${hours} hours`;
    }
  }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.item = this.itemService.getItemById(Number(id));
    }
  }

  addToCart() {
    this.cartService.addToCart(this.item);
  }
}
