import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrStep1Component } from './cr-step1.component';

describe('CrStep1Component', () => {
  let component: CrStep1Component;
  let fixture: ComponentFixture<CrStep1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrStep1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrStep1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
