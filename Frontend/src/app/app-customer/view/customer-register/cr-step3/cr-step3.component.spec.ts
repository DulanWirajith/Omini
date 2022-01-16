import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrStep3Component } from './cr-step3.component';

describe('CrStep3Component', () => {
  let component: CrStep3Component;
  let fixture: ComponentFixture<CrStep3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrStep3Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrStep3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
