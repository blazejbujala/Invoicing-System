import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CompanyService} from "../../services/company.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {InvoiceService} from "../../services/invoice.service";
import {CompanyDto} from "../../model/company.dto";
import {InvoiceModel} from "../../model/InvoiceModel";
import {InvoiceEntryModel} from "../../model/InvoiceEntryModel";

@Component({
  selector: 'app-invoice-add',
  templateUrl: './invoice-add.component.html',
  styleUrls: ['./invoice-add.component.scss']
})
export class InvoiceAddComponent implements OnInit {

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
  invoiceAddForm: FormGroup;
  entriesForm: FormGroup;


  constructor(private companyService: CompanyService, private invoiceService: InvoiceService, private formBuilder: FormBuilder, private router: Router,
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

    this.invoiceAddForm = this.formBuilder.group(
      {
        invoiceNumber: ['', [Validators.required]],
        dateOfIssue: ['', [Validators.required]],
        issuerId: ['', [Validators.required]],
        receiverId: ['', [Validators.required]],
      }
    )
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

  save(): void {
    this.invoice.dateOfIssue = this.invoiceAddForm.controls['dateOfIssue'].value
    this.invoice.invoiceNumber = this.invoiceAddForm.controls['invoiceNumber'].value
    this.invoiceService.save(this.invoice).subscribe(() => {
          this.toastService.success("Invoice added");
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
