import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {AppCustomerModule} from "../app-customer.module";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ShopCartService {

  shopCartSub = new Subject<any>();
  shopCart = [];
  shopCartItemsSub = new Subject<any>();
  initShopCartSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  addCart(cart): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'customer_profile/addCart', cart);
  }

  getCart(cartId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'customer_profile/getCart/' + cartId);
  }

  addOrder(order) {
    return this.http.post<any>(environment.backend_url + 'item_order/addOrder', order);
  }

  // getNewShopCart(){
  //   return {
  //     shop: "",
  //     items: []
  //   }
  // }
}
