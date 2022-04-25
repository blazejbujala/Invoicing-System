import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {CompanyDto} from "../model/company.dto";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
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
    return this.httpClient.get<Array<CompanyDto>>(`${environment.apiBasePath}${this.path}`)
  }

  public save(item: CompanyDto): Observable<CompanyDto>{
    return this.httpClient.post<CompanyDto>(`${environment.apiBasePath}${this.path}`, item);
  }

  public get(id: string): Observable<CompanyDto>{
    return this.httpClient.get<CompanyDto>(`${environment.apiBasePath}${this.path}/${id}`)
  }

  public update(id: string, item: CompanyDto): Observable<CompanyDto>{
    return this.httpClient.patch<CompanyDto>(`${environment.apiBasePath}${this.path}/${id}`, item)
  }

  public delete(id: string): Observable<any>{
    return this.httpClient.delete<void>(`${environment.apiBasePath}${this.path}/${id}`)
  }

  public getTaxReportFormDataBase(item: CompanyDto): Observable<TaxReportModel>{
    return this.httpClient.post<TaxReportModel>(`${environment.apiBasePath}tax`, item);
  }
}
