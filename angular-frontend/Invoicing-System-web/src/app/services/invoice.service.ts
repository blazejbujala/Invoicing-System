import {Injectable} from '@angular/core';
import {commons} from "../../environments/commons";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvoiceModel} from "../model/InvoiceModel";
import {CompanyDto} from "../model/company.dto";

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private path = 'invoices'

  constructor(private httpClient: HttpClient) {

  }

  public getInvoicesList(): Observable<Array<InvoiceModel>> {
    return this.httpClient.get<Array<InvoiceModel>>(`${commons.apiBasePath}${this.path}`)
  }

  public get(id: string): Observable<InvoiceModel> {
    return this.httpClient.get<InvoiceModel>(`${commons.apiBasePath}${this.path}/${id}`)
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete<void>(`${commons.apiBasePath}${this.path}/${id}`)
  }

  public save(item: InvoiceModel): Observable<InvoiceModel> {
    return this.httpClient.post<InvoiceModel>(`${commons.apiBasePath}${this.path}`, item);
  }

}
