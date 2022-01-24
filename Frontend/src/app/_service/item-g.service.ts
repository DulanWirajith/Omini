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

  getItemPackageSelected(itemPackageId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getItemPackageSelected/' + itemPackageId);
  }

  addItemReview(itemReview): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item/addItemReview', itemReview);
  }

  addItemResponse(itemReviewResponse): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item/addItemResponse', itemReviewResponse);
  }

  getItemReviews(itemId, customerId, reviewType): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemReviews/' + itemId + '/' + customerId + '/' + reviewType);
  }
}
