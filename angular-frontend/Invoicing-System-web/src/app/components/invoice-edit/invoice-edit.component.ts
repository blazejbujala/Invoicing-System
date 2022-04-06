import {Component, OnInit} from '@angular/core';
import {InvoiceEntryModel} from "../../model/InvoiceEntryModel";
import {CompanyDto} from "../../model/company.dto";
import {InvoiceModel} from "../../model/InvoiceModel";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CompanyService} from "../../services/company.service";
import {InvoiceService} from "../../services/invoice.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-invoice-edit',
  templateUrl: './invoice-edit.component.html',
  styleUrls: ['./invoice-edit.component.scss']
})
export class InvoiceEditComponent implements OnInit {

  invoiceId: string | null

  public entry: InvoiceEntryModel = {
    "entryId": 0,
    "description": '',
    "price": 0,
    "quantity": 0,
    "totalPrice": 0,
    "vatValue": 0,
    "vatRate": '',
    "totalGrossPrice": 0,
    "carRegNo": '',
    "personalUseCar": false,
  }

  public issuer: CompanyDto = {
    "companyId": '',
    "taxIdentificationNumber": '',
    "name": '',
    "address": '',
    "healthInsurance": 0,
    "pensionInsurance": 0,
  }

  public receiver: CompanyDto = {
    "companyId": '',
    "taxIdentificationNumber": '',
    "name": '',
    "address": '',
    "healthInsurance": 0,
    "pensionInsurance": 0,
  }

  public invoice: InvoiceModel = {
    'invoiceId': '',
    "invoiceNumber": '',
    "dateOfIssue": '',
    "issuer": this.issuer,
    "receiver": this.receiver,
    "entries": []
  }

  selectedIssuer: string = ''
  selectedReceiver: string = ''
  selectedVatRate: string = ''
  companies: Array<CompanyDto> = []
  invoiceEditForm: FormGroup;
  entriesForm: FormGroup;


  constructor(private activatedRoute: ActivatedRoute, private companyService: CompanyService, private invoiceService: InvoiceService, private formBuilder: FormBuilder, private router: Router,
              private toastService: ToastrService) {
    this.invoiceId = ''

    this.entriesForm = this.formBuilder.group(
      {
        description: ['', [Validators.required,]],
        price: ['', [Validators.required,]],
        quantity: ['', [Validators.required,]],
        vatRate: [this.selectedVatRate, [Validators.required]],
        carRegNo: ['', []],
        personalUseCar: [false, []]
      }
    )

    this.invoiceEditForm = this.formBuilder.group(
      {
        invoiceNumber: ['', [Validators.required]],
        dateOfIssue: ['', [Validators.required]],
        issuerId: ['', [Validators.required]],
        receiverId: ['', [Validators.required]],
      }
    )
  }

  ngOnInit(): void {
    this.invoiceId = this.activatedRoute.snapshot.paramMap.get('id')
    if (this.invoiceId != null) {
      this.invoiceService.get(this.invoiceId).subscribe(data => {
        this.invoice = data
        this.invoiceEditForm.patchValue({...data})
      }, error => {
        this.toastService.error("Something went wrong")
      })
    }
    {
    }
    this.companyService.getCompanyList().subscribe(data => {
        this.companies = data
      },
      error => {
        console.log(error)
      }
    )
  }

  updateReceiver(id: string): void {
    if (!!id)
      this.companyService.get(id).subscribe(data => {
          this.invoice.receiver = data
        },
        error => {
          console.log(error)
        }
      )
  }

  updateIssuer(id: string): void {
    if (!!id)
      this.companyService.get(id).subscribe(data => {
          this.invoice.issuer = data
        },
        error => {
          console.log(error)
        }
      )
  }

  update(): void {
    this.invoice.dateOfIssue = this.invoiceEditForm.controls['dateOfIssue'].value
    this.invoice.invoiceNumber = this.invoiceEditForm.controls['invoiceNumber'].value
    this.invoiceService.update(this.invoice.invoiceId, this.invoice).subscribe(() => {
        this.toastService.success("Invoice updated");
        this.router.navigate(['invoices'])
      },
      error => {
        console.log(error)
      }
    )
  }

  addEntry(): void {
    this.invoice.entries.push(
      {
        ...this.entriesForm.value,
      }
    )
  }

  deleteEntry(i: number): void {
    this.invoice.entries.splice(i, 1)
  }

  cancel() {
    this.router.navigate(['invoices'])
  }

}
