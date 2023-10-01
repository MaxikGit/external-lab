import { TestBed } from '@angular/core/testing';

import { ItemsDummydataService } from './items-dummydata.service';

describe('ItemsDummydataService', () => {
  let service: ItemsDummydataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemsDummydataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
