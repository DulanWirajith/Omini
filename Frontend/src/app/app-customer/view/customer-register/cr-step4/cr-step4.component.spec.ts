import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrStep4Component } from './cr-step4.component';

describe('CrStep4Component', () => {
  let component: CrStep4Component;
  let fixture: ComponentFixture<CrStep4Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrStep4Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrStep4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
