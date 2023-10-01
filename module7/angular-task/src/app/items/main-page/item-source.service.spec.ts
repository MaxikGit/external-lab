import { TestBed } from '@angular/core/testing';

import { ItemSourceService } from './item-source.service';

describe('ItemSourceService', () => {
  let service: ItemSourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemSourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
