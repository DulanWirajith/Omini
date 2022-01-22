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

  updateOrderDetail(updateType, orderDetail) {
    return this.http.put<any>(environment.backend_url + 'item_order/updateOrderDetail/' + updateType, orderDetail);
  }

  getOrder(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/getIncOrder/' + customerId);
  }

  placeOrder(orderId) {
    return this.http.post<any>(environment.backend_url + 'item_order/placeOrder', orderId);
  }

  removeCart(orderId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/removeCart/' + orderId);
  }

  removeCartItem(orderDetailId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/removeCartItem/' + orderDetailId);
  }

  removeCartShop(orderId, businessProfileId, businessCategoryId) {
    return this.http.delete<any>(environment.backend_url + 'item_order/removeCartShop/' + orderId + '/' + businessProfileId + '/' + businessCategoryId);
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
