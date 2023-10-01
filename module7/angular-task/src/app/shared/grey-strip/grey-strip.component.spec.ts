import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GreyStripComponent } from './grey-strip.component';

describe('GreyStripComponent', () => {
  let component: GreyStripComponent;
  let fixture: ComponentFixture<GreyStripComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GreyStripComponent]
    });
    fixture = TestBed.createComponent(GreyStripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
