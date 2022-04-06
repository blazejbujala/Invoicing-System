import {Component, OnInit} from '@angular/core';
import {TaxReportModel} from "../../model/TaxReportModel";
import {ActivatedRoute, Router} from "@angular/router";
import {CompanyService} from "../../services/company.service";
import {ToastrService} from "ngx-toastr";
import {CompanyDto} from "../../model/company.dto";

@Component({
  selector: 'app-company-tax-report',
  templateUrl: './company-tax-report.component.html',
  styleUrls: ['./company-tax-report.component.scss']
})
export class CompanyTaxReportComponent implements OnInit {

  id: string | null = null

  taxReport: TaxReportModel = {
    incomingVat: 0,
    outgoingVat: 0,
    vatToPay: 0,
    income: 0,
    costs: 0,
    earnings: 0,
    pensionInsurance: 0,
    taxBase: 0,
    taxBaseRounded: 0,
    incomeTax: 0,
    healthInsuranceNinePercent: 0,
    healthInsuranceSevenAndSeventyFivePercent: 0,
    incomeTaxReducedByHealthInsurance: 0,
    finalIncomeTaxValue: 0
  }

  company: CompanyDto = {
    companyId: '',
    taxIdentificationNumber: '',
    name: '',
    address: '',
    healthInsurance: 0,
    pensionInsurance: 0
  }

  constructor(private activatedRoute: ActivatedRoute, private companyService: CompanyService, private toastService: ToastrService, private router: Router,) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.paramMap.get('id')
    if (this.id != null) {
      this.companyService.get(this.id).subscribe(data => {
          this.company = data
          this.companyService.getTaxReportFormDataBase(this.company).subscribe(data => {
              this.taxReport = data
              console.log(this.taxReport)
            },
            error => {
              console.log(error)
            }
          )
        },
        error => {
          console.log(error)
        }
      )
    }
  }
  navigateToPreview() {
    this.id = this.activatedRoute.snapshot.paramMap.get('id')
    this.router.navigate(['companies', this.id])
  }
}
