export class InvoiceEntryModel {

  constructor(
    public entryId: number,
    public description: string,
    public price: number,
    public quantity: number,
    public totalPrice: number,
    public vatValue: number,
    public vatRate: string,
    public carRegNo: string,
    public personalUseCar: boolean
  ){

  }
}


