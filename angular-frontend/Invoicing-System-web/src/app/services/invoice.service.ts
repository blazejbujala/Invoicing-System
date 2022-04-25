import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvoiceModel} from "../model/InvoiceModel";

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private path = 'invoices'

  constructor(private httpClient: HttpClient) {

  }

  public getInvoicesList(): Observable<Array<InvoiceModel>> {
    return this.httpClient.get<Array<InvoiceModel>>(`${environment.apiBasePath}${this.path}`)
  }

  public save(item: InvoiceModel): Observable<InvoiceModel>{
    return this.httpClient.post<InvoiceModel>(`${environment.apiBasePath}${this.path}`, item);
  }

  public get(id: string): Observable<InvoiceModel> {
    return this.httpClient.get<InvoiceModel>(`${environment.apiBasePath}${this.path}/${id}`)
  }

  public update(id: string, item: InvoiceModel): Observable<InvoiceModel>{
    return this.httpClient.patch<InvoiceModel>(`${environment.apiBasePath}${this.path}/${id}`, item)
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete<void>(`${environment.apiBasePath}${this.path}/${id}`)
  }
}
