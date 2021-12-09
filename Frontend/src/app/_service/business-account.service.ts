import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BusinessAccountService {

  breadCrumbSub = new Subject<string>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  // setText(txt) {
  //   this.breadCrumbSub.next(txt)
  // }

  addItem(item): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item/addItem', item);
  }

  addPackage(itemsPackage): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'package/addPackage', itemsPackage);
  }

  getItemFeatures(businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_feature/getItemFeatures/' + businessCategoryId);
  }

  getItemsBusinessCategory(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemsBusinessCategory/' + businessProfileId + '/' + businessCategoryId);
  }
}
