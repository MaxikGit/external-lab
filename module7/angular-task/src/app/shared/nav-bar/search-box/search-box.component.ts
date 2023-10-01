import {Component, OnDestroy, OnInit} from '@angular/core';
import {CategoryService} from "./category.service";
import {FormBuilder, FormControl} from "@angular/forms";
import {
  debounceTime,
  distinctUntilChanged,
  Subscription,
  tap
} from "rxjs";
import {FilterService} from "../../../items/filter.service";

@Component({
  selector: 'search-box',
  templateUrl: './search-box.component.html',
  styleUrls: ['./search-box.component.css']
})
export class SearchBoxComponent implements OnInit, OnDestroy {

  categorySearch!: FormControl;
  categories: string[] = this.categoryService.getCategories();
  itemSearch!: FormControl;
  private searchSubscription?: Subscription;

  constructor(private categoryService: CategoryService, private searchService: FilterService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.categorySearch = this.formBuilder.control('');
    this.itemSearch = this.formBuilder.control('');

    this.itemSearch.valueChanges.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      tap(searchQuery => {
        this.searchService.setSearchQuery(searchQuery);
      })
    ).subscribe();
  }

  filterByCategory(category: string) {
    this.searchService.setCategoryFilter(category)
  }

  resetCategories() {
    this.filterByCategory('');
  }

  ngOnDestroy(): void {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
      console.log('search box search unsubscribed')
    }
  }
}
