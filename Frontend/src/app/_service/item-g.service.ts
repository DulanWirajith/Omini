import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ItemGService {

  itemSub = new Subject<any>();
  itemPackageSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  getItemSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
  }
}
