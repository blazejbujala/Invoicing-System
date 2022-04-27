import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { CompanyListComponent } from './components/company-list/company-list.component';
import { InvoicesListComponent } from './components/invoices-list/invoices-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CompanyAddComponent } from './components/company-add/company-add.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import { CompanyPreviewComponent } from './components/company-preview/company-preview.component';
import { InvoicePreviewComponent } from './components/invoice-preview/invoice-preview.component';
import { CompanyTaxReportComponent } from './components/company-tax-report/company-tax-report.component';
import { InvoiceAddComponent } from './components/invoice-add/invoice-add.component';
import { InvoiceEditComponent } from './components/invoice-edit/invoice-edit.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent,
    CompanyListComponent,
    InvoicesListComponent,
    CompanyAddComponent,
    CompanyPreviewComponent,
    InvoicePreviewComponent,
    CompanyTaxReportComponent,
    InvoiceAddComponent,
    InvoiceEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
