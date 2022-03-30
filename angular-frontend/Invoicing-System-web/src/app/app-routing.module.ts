import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CompanyListComponent} from "./components/company-list/company-list.component";
import {InvoicesListComponent} from "./components/invoices-list/invoices-list.component";
import {CompanyAddComponent} from "./components/company-add/company-add.component";
import {CompanyPreviewComponent} from "./components/company-preview/company-preview.component";
import {InvoicePreviewComponent} from "./components/invoice-preview/invoice-preview.component";
import {CompanyTaxReportComponent} from "./components/company-tax-report/company-tax-report.component";
import {InvoiceAddComponent} from "./components/invoice-add/invoice-add.component";

const routes: Routes = [
  {
    path: "companies",
    children: [
      {
        path: 'new',
        component: CompanyAddComponent
      },
      {
        path: ':id',
        component: CompanyPreviewComponent
      },
      {
        path: '',
        component: CompanyListComponent
      }
    ]
  },
  {
    path: "invoices",
    children: [
      {
        path: 'new',
        component: InvoiceAddComponent
      },
      {
        path: ':id',
        component: InvoicePreviewComponent
      },
      {
        path: '',
        component: InvoicesListComponent
      }

    ]
  },
  {
    path: 'tax',
    children: [
      {
        path: ':id',
        component: CompanyTaxReportComponent
      }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
