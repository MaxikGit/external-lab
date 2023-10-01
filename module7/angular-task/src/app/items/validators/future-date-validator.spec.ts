import {FutureDateValidator} from "./future-date-validator";
import {FormControl} from "@angular/forms";

describe('FutureDateValidator', () => {
  it('should create an instance', () => {
    expect(new FutureDateValidator()).toBeTruthy();
  });


  it('should return true if future', () => {
    let testControl = new FormControl(new Date(Date.now() + 1000));
    testControl.addValidators([FutureDateValidator.validateDateIsFuture]);
    expect(testControl.valid).toBeTrue();
    expect(FutureDateValidator.validateDateIsFuture(testControl)).toBeNull();
  });

  it('should return false if current date', () => {
    let testControl = new FormControl(new Date(Date.now()));
    testControl.addValidators([FutureDateValidator.validateDateIsFuture]);
    expect(FutureDateValidator.validateDateIsFuture(testControl)).toBeTruthy();
  });
});
