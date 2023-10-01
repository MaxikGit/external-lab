import { TestBed } from '@angular/core/testing';
import {CanDeactivateFn} from '@angular/router';

import {FormInputComponent} from "../form-input-component";
import {formDeactivateGuard} from "./can-deactivate-form.guard";

describe('canDeactivateGuard', () => {
  const executeGuard: CanDeactivateFn<FormInputComponent> = (...guardParameters) =>
      TestBed.runInInjectionContext(() => formDeactivateGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
