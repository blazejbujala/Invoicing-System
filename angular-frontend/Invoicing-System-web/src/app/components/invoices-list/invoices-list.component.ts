import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../../services/company.service";
import {CompanyDto} from "../../model/company.dto";
import {Router} from "@angular/router";
import {InvoiceModel} from "../../model/InvoiceModel";
import {InvoiceService} from "../../services/invoice.service";

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss']
})
export class InvoicesListComponent implements OnInit {
  invoices: Array<InvoiceModel> = []

  constructor(private invoiceService: InvoiceService, private router: Router) {
  }

  ngOnInit(): void {
    this.invoiceService.getInvoicesList().subscribe(data => {
        this.invoices = data
      },
      error => {
        console.log(error)
      }
    )
  }

  navigateToPreview(id: string) {
    this.router.navigate(['invoices', id])
  }

  navigateToAddForm(){
    this.router.navigate(['invoices/new'])
  }

}
