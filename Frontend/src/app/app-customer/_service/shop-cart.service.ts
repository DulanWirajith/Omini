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

  iniCart = true;
  shopCartSub = new Subject<any>();
  // shopCart = [];
  orderDetails = [];
  shopCartItemsSub = new Subject<any>();
  initShopCartSub = new Subject<any>();

  shopCart = [];
  itemOrder;
  totalItemCount = 0;
  totalPrice = 0;

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  addOrderDetail(order) {
    return this.http.post<any>(environment.backend_url + 'item_order/customer/addOrderDetail', order, {headers: this.commonService.createAuthorizationHeader()});
  }

  updateOrderDetail(updateType, orderDetail) {
    return this.http.put<any>(environment.backend_url + 'item_order/customer/updateOrderDetail/' + updateType, orderDetail, {headers: this.commonService.createAuthorizationHeader()});
  }

  getOrder(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/customer/getIncOrder/' + customerId, {headers: this.commonService.createAuthorizationHeader()});
  }

  placeOrder(orderId) {
    return this.http.post<any>(environment.backend_url + 'item_order/customer/placeOrder', orderId, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeCart(orderId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/customer/removeCart/' + orderId, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeCartItem(orderDetailId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/customer/removeCartItem/' + orderDetailId, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeCartShop(orderId, businessProfileId, businessCategoryId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/customer/removeCartShop/' + orderId + '/' + businessProfileId + '/' + businessCategoryId, {headers: this.commonService.createAuthorizationHeader()});
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
      orderDetails: [],
      orderDate: ''
    }
  }

  getNewOrderDetail() {
    return {
      orderDetailId: '',
      quantity: 0,
      price: 0,
      discountedPrice: 0,
      discount: 0,
      orderDetailType: '',
      itemOrder: undefined,
      itemPackage: undefined,
      item: undefined,
      businessProfileCategory: undefined
    }
  }
}
