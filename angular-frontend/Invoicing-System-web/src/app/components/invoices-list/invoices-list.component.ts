import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../../services/company.service";
import {CompanyDto} from "../../model/company.dto";
import {InvoiceModel} from "../../model/InvoiceModel";
import {InvoiceService} from "../../services/invoice.service";
import {faTrash} from '@fortawesome/free-solid-svg-icons';
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss']
})
export class InvoicesListComponent implements OnInit {
  invoices: Array<InvoiceModel> = []
  faTrash = faTrash

  constructor(private invoiceService: InvoiceService, private router: Router, private toastService: ToastrService) {
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

    delete(id: string) {
      this.invoiceService.delete(id).subscribe(data => {
          this.toastService.success("Invoice deleted"),
            this.router.navigate(['invoices'])
        },
        error => {
          this.toastService.error("Something went wrong")
        })
    }

}
