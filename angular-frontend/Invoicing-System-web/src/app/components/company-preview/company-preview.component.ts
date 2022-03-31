import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CompanyService} from "../../services/company.service";
import {ToastrService} from "ngx-toastr";
import {CompanyDto} from "../../model/company.dto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-company-preview',
  templateUrl: './company-preview.component.html',
  styleUrls: ['./company-preview.component.scss']
})
export class CompanyPreviewComponent implements OnInit {

  companyUpdateFormGroup: FormGroup;

  id: string | null = null
  company: CompanyDto = {
    companyId: '',
    taxIdentificationNumber: '',
    name: '',
    address: '',
    healthInsurance: 0,
    pensionInsurance: 0
  }

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder,
              private companyService: CompanyService, private toastService: ToastrService, private router: Router,) {
    this.companyUpdateFormGroup = this.formBuilder.group({
        companyId: [{value: '', disabled: true}, []],
        taxIdentificationNumber: ['', [Validators.required, Validators.pattern("^\d{10}$")]],
        name: ['', [Validators.required]],
        address: ['', [Validators.required]],
        healthInsurance: ['', [Validators.required, Validators.pattern("^\d*[.]?\d{0,2}$")]],
        pensionInsurance: ['', [Validators.required, Validators.pattern("^\d*[.]?\d{0,2}$")]]
      }
    )
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.paramMap.get('id')

    if (this.id != null) {
      this.companyService.get(this.id).subscribe(data => {
        this.company = data
        this.companyUpdateFormGroup.patchValue({...data})
      }, error => {
        this.toastService.error("Something went wrong")
      })
    }
  }

  delete(id: string) {
    this.companyService.delete(id).subscribe(data => {
        this.toastService.success("Company deleted"),
          this.router.navigate(['companies'])
      },
      error => {
        this.toastService.error("Something went wrong")
      })
  }

  update(id: string) {
    console.log(this.companyUpdateFormGroup.getRawValue())
    this.companyService.update(id, this.companyUpdateFormGroup.getRawValue()).subscribe(data => {
        this.toastService.success("Company details changed");
        this.router.navigate(['companies'])
      },
      error => {
        this.toastService.error("Something went wrong")
      })
  }

  navigateToCompanyList() {
    this.router.navigate(['companies'])
  }

  navigateToTaxReport(id: string) {
    this.router.navigate(['tax', this.id])
  }
}
