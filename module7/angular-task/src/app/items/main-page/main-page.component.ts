import {Component, OnDestroy, OnInit} from '@angular/core';
import {ItemSourceService} from "./item-source.service";
import {Subscription} from "rxjs";
import {Item} from "../item";
import {FilterService} from "../filter.service";
import {AlphaNumericSortService} from "../../shared/alpha-numeric-sort.service";
import {IsLoadingService} from "../is-loading.service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit, OnDestroy {

  items?: Item[];
  isLoading = false;
  distance = 1; //When the user scrolls 90% of the way down the page, onScroll() will be called if [infiniteScrollDistance] is set to 1. If two, then 80%
  throttle = 2000; //If [infiniteScrollThrottle] is set to 500, onScroll() won’t run until the user has not scrolled for 500 milliseconds.

  private sourceSubscription?: Subscription;

  constructor(private itemSource: ItemSourceService, private filterService: FilterService,
              private nameSortService: AlphaNumericSortService, private isLoadingsService: IsLoadingService) {
  }

  ngOnInit(): void {
    this.isLoadingsService.isLoading$.subscribe(data => this.isLoading = data);
    this.sourceSubscription = this.filterService.filterContent(
      this.itemSource.getItems()
    )
      .subscribe(data => {
        this.items = data.sort(
          (a, b) => this.nameSortService.sort(a.name, b.name)
        );
      });
  }

  onScroll() {
    this.itemSource.loadData();
  }

  ngOnDestroy(): void {
    if (this.sourceSubscription) {
      console.log('unsubscribed from items')
      this.sourceSubscription.unsubscribe();
    }
  }
}
