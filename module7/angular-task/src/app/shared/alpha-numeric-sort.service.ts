import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlphaNumericSortService {

  constructor() { }

  sort(a: string, b: string): number {

    return a.localeCompare(b, undefined, { numeric: true, sensitivity: 'base' });
  }
}
