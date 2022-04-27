import {Component, OnInit} from '@angular/core';
import {CompanyDto} from "../../model/company.dto";
import {CompanyService} from "../../services/company.service";
import {faTrash} from '@fortawesome/free-solid-svg-icons';
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit {

  companies: Array<CompanyDto> = []
  faTrash = faTrash;

  constructor(private companyService: CompanyService, private router: Router, private toastService: ToastrService,) {
  }

  ngOnInit(): void {
    this.companyService.getCompanyList().subscribe(data => {
        this.companies = data
      },
      error => {
        console.log(error)
      }
    )
  }

  navigateToAddForm(){
    this.router.navigate(['companies/new'])
  }

  navigateToPreview(id: string){
  this.router.navigate(['companies',id])
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
}
