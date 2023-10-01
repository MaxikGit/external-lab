import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { loginMatchGuard } from './loginMatchGuard';

describe('loginGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) =>
      TestBed.runInInjectionContext(() => loginMatchGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
