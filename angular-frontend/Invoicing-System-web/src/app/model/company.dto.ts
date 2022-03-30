export class CompanyDto {

  constructor(
    public companyId: string,
    public taxIdentificationNumber: string,
    public name: string,
    public address: string,
    public healthInsurance: number,
    public pensionInsurance: number,
  ) {
  }
}
