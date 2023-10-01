import { TestBed } from '@angular/core/testing';

import { AlphaNumericSortService } from './alpha-numeric-sort.service';

describe('AlphaNumericSortService', () => {
  let service: AlphaNumericSortService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlphaNumericSortService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
