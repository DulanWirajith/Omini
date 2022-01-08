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
  orderDetails = [];
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

  addOrderDetail(order) {
    return this.http.post<any>(environment.backend_url + 'item_order/addOrderDetail', order);
  }

  updateOrderDetail(orderDetail) {
    return this.http.put<any>(environment.backend_url + 'item_order/updateOrderDetail', orderDetail);
  }

  getOrder(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/getIncOrder/' + customerId);
  }

  getNewItemOrder() {
    return {
      orderId: '',
      // orderDate: '',
      amount: 0,
      discount: 0,
      qty: 0,
      status: '',
      // receivedTime: '',
      customerProfile: {
        customerProId: ''
      },
      orderDetails: []
    }
  }

  getNewOrderDetail() {
    return {
      orderDetailId: '',
      quantity: 0,
      price: 0,
      discount: 0,
      orderDetailType: '',
      itemOrder: undefined,
      itemPackage: undefined,
      item: undefined
    }
  }
}
