import {Component, OnInit} from '@angular/core';
import {CompanyDto} from "../../model/company.dto";
import {CompanyService} from "../../services/company.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit {

  companies: Array<CompanyDto> = []

  constructor(private companyService: CompanyService, private router: Router) {
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
}
