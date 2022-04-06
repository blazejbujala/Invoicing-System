import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {CompanyDto} from "../model/company.dto";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {commons} from "../../environments/commons";
import {CompanyTaxReportComponent} from "../components/company-tax-report/company-tax-report.component";
import {TaxReportModel} from "../model/TaxReportModel";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private path = 'companies'

  constructor(private httpClient: HttpClient) {
  }

  public getCompanyList(): Observable<Array<CompanyDto>> {
    return this.httpClient.get<Array<CompanyDto>>(`${commons.apiBasePath}${this.path}`)
  }

  public save(item: CompanyDto): Observable<CompanyDto>{
    return this.httpClient.post<CompanyDto>(`${commons.apiBasePath}${this.path}`, item);
  }

  public get(id: string): Observable<CompanyDto>{
    return this.httpClient.get<CompanyDto>(`${commons.apiBasePath}${this.path}/${id}`)
  }

  public update(id: string, item: CompanyDto): Observable<CompanyDto>{
    return this.httpClient.patch<CompanyDto>(`${commons.apiBasePath}${this.path}/${id}`, item)
  }

  public delete(id: string): Observable<any>{
    return this.httpClient.delete<void>(`${commons.apiBasePath}${this.path}/${id}`)
  }

  public getTaxReportFormDataBase(item: CompanyDto): Observable<TaxReportModel>{
    return this.httpClient.post<TaxReportModel>(`${commons.apiBasePath}tax`, item);
  }
}
