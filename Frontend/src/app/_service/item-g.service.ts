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
    return this.http.get<any>(environment.backend_url + 'item_package/customer/setItemPackageFavourite/' + customerId + '/' + itemId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addItemPackageReview(itemReview): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item_package/customer/addItemPackageReview', itemReview, {headers: this.commonService.createAuthorizationHeader()});
  }

  updateItemPackageReview(itemReview, reviewId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'item_package/customer/updateItemPackageReview/' + reviewId, itemReview, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeItemPackageReview(reviewId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'item_package/customer/removeItemPackageReview/' + reviewId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addItemPackageResponse(itemReviewResponse): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item_package/customer/addItemPackageResponse', itemReviewResponse, {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemPackageReviews(itemId, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageReviews/' + itemId + '/' + customerId);
  }
}
