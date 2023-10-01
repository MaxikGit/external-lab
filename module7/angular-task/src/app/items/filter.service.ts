import {Injectable} from '@angular/core';
import {BehaviorSubject, combineLatest, map, Observable, switchMap} from "rxjs";
import {Item} from "./item";

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  private searchQuery = new BehaviorSubject<string>('');
  private categoryFilter = new BehaviorSubject<string>(''); // Add a behavior subject for category filtering

  constructor() {}

  setSearchQuery(query: string) {
    this.searchQuery.next(query);
  }

  setCategoryFilter(category: string) {
    this.categoryFilter.next(category);
  }

  filterContent(source$: Observable<Item[]>): Observable<Item[]> {
    return combineLatest([source$, this.searchQuery, this.categoryFilter]).pipe(
      switchMap(([items, searchQuery, category]) => {
        return source$.pipe(
          map(items => {
            return items.filter(item => {
              const matchesSearch = item.name.toLowerCase().includes(searchQuery.toLowerCase());
              const matchesCategory = category === '' || item.tags.includes(category as string);
              return matchesSearch && matchesCategory;
            });
          })
        );
      })
    );
  }
}
