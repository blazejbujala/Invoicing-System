import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../../services/company.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-company-add',
  templateUrl: './company-add.component.html',
  styleUrls: ['./company-add.component.scss']
})
export class CompanyAddComponent implements OnInit {

  companyAddFormGroup: FormGroup;

  constructor(private companyService: CompanyService, private formBuilder: FormBuilder, private router: Router,
              private toastService: ToastrService) {
    this.companyAddFormGroup = this.formBuilder.group({
        taxIdentificationNumber: ['', [Validators.required, Validators.pattern("^\d{10}$")]],
        name: ['', [Validators.required]],
        address: ['', [Validators.required]],
        healthInsurance: ['', [Validators.required, Validators.pattern("^\d*[.]?\d{0,2}$")]],
        pensionInsurance: ['', [Validators.required, Validators.pattern("^\d*[.]?\d{0,2}$")]]
      }
    )
  }

  ngOnInit(): void {
  }

  save() {
    this.companyService.save(this.companyAddFormGroup.getRawValue()).subscribe(data => {
        this.toastService.success("Company added");
        this.router.navigate(['companies'])
      },
      error => {
        this.toastService.error("Something went wrong")
      })
  }

  cancel() {
    this.router.navigate(['companies'])
  }

}
