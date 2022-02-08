import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ItemGService {

  itemPackageSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  getItemPackageSelected(itemId, type, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageSelected/' + itemId + '/' + type + '/' + customerId);
  }

  setItemFavourite(customerId, itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/setItemPackageFavourite/' + customerId + '/' + itemId);
  }

  addItemPackageReview(itemReview): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item_package/addItemPackageReview', itemReview);
  }

  addItemPackageResponse(itemReviewResponse): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item_package/addItemPackageResponse', itemReviewResponse);
  }

  getItemPackageReviews(itemId, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageReviews/' + itemId + '/' + customerId);
  }
}
