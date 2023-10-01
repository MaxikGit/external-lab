import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapseButtonRowComponent } from './collapse-button-row.component';

describe('CollapseButtonRowComponent', () => {
  let component: CollapseButtonRowComponent;
  let fixture: ComponentFixture<CollapseButtonRowComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CollapseButtonRowComponent]
    });
    fixture = TestBed.createComponent(CollapseButtonRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
