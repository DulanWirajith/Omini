import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {ShopCartService} from "./shop-cart.service";

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  itemSub = new Subject<any>();
  searchedItemPackages = {
    items: [],
    itemPackages: []
  };

  constructor(private http: HttpClient, private commonService: CommonService, private shopCartService: ShopCartService) {
  }

  getItemsPackagesBySearch(txt, category): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemsPackagesBySearch/' + txt + '/' + category);
  }

  getItemPackageSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemPackageSelected/' + itemId);
  }

  getCustomerOrders(customerId, status, from, to): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/getCustomerOrders/' + customerId + '/' + status + '/' + from + '/' + to);
  }

  getPackageItemSelected(itemPackageId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getPackageItemSelected/' + itemPackageId);
  }

  setItemFavourite(customerId, itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/setItemFavourite/' + customerId + '/' + itemId);
  }

  getNewItem() {
    return {
      itemId: "",
      itemPackage: this.getNewItemPackage()
    }
  }

  getNewItemPackage() {
    return {
      itemPackageId: "",
      orderDetailId: '',
      name: "",
      quantity: 1,
      makeToOrder: false,
      itemCount: 0,
      discount: "",
      discountType: "None",
      price: "",
      favourite: false,
      description: "",
      itemPackageImages: [],
      itemImgsRaw: [],
      itemAvailable: false,
      businessProfileCategory: {
        businessProfile: {
          businessProfile: undefined
        },
        businessCategory: undefined
      },
      itemPackageItemPackageFeatures: [],
      isNewItem: false,
      isUpdateItem: false,
      orderDetail: undefined,
      discountedPrice: 0
    }
  }
}
