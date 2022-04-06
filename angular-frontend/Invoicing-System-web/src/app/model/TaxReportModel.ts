export class TaxReportModel {

  constructor(
    public incomingVat: number,
    public outgoingVat: number,
    public vatToPay: number,
    public income: number,
    public costs: number,
    public earnings: number,
    public pensionInsurance: number,
    public taxBase: number,
    public taxBaseRounded: number,
    public incomeTax: number,
    public healthInsuranceNinePercent: number,
    public healthInsuranceSevenAndSeventyFivePercent: number,
    public incomeTaxReducedByHealthInsurance: number,
    public finalIncomeTaxValue: number,
  ) {
  }
}
