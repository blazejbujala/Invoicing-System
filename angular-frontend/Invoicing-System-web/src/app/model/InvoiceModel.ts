import {InvoiceEntryModel} from "./InvoiceEntryModel";
import {CompanyDto} from "./company.dto";

export class InvoiceModel {

  constructor(
    public invoiceId: string,
    public invoiceNumber: string,
    public dateOfIssue: string,
    public issuer: CompanyDto,
    public receiver: CompanyDto,
    public entries: Array<InvoiceEntryModel>
  ) {
  }
}
