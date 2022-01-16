import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrStep2Component } from './cr-step2.component';

describe('CrStep2Component', () => {
  let component: CrStep2Component;
  let fixture: ComponentFixture<CrStep2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrStep2Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrStep2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
