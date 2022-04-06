import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {InvoiceService} from "../../services/invoice.service";
import {InvoiceModel} from "../../model/InvoiceModel";
import {CompanyDto} from "../../model/company.dto";
import {InvoiceEntryModel} from "../../model/InvoiceEntryModel";

@Component({
  selector: 'app-invoice-preview',
  templateUrl: './invoice-preview.component.html',
  styleUrls: ['./invoice-preview.component.scss']
})
export class InvoicePreviewComponent implements OnInit {

  id: string | null = null

  issuer: CompanyDto = {
    companyId: '',
    taxIdentificationNumber: '',
    name: '',
    address: '',
    healthInsurance: 0,
    pensionInsurance: 0
  }

  receiver: CompanyDto = {
    companyId: '',
    taxIdentificationNumber: '',
    name: '',
    address: '',
    healthInsurance: 0,
    pensionInsurance: 0
  }

  entries: Array<InvoiceEntryModel> = []

  invoice: InvoiceModel = {
    invoiceId: '',
    invoiceNumber: '',
    dateOfIssue: '',
    issuer: this.issuer,
    receiver: this.receiver,
    entries: this.entries
  }

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder,
              private invoiceService: InvoiceService, private toastService: ToastrService, private router: Router,) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.paramMap.get('id')

    if (this.id != null) {
      this.invoiceService.get(this.id).subscribe(data => {
        this.invoice = data;
      }, error => {
        this.toastService.error("Something went wrong")
      })
    }
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

  navigateToInvoiceList() {
    this.router.navigate(['invoices'])
  }

  navigateToInvoiceEdit(id: string){
    this.router.navigate(['invoices/edit', id])
  }
}
