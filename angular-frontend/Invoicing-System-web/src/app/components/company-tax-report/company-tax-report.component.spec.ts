import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyTaxReportComponent } from './company-tax-report.component';

describe('CompanyTaxReportComponent', () => {
  let component: CompanyTaxReportComponent;
  let fixture: ComponentFixture<CompanyTaxReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompanyTaxReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyTaxReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
