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

  searchedItemPackages = {
    items: [],
    itemPackages: []
  };
  searchedShops = [];
  searchedItemPackagesSub = new Subject<any>();
  searchedShopsSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService, private shopCartService: ShopCartService) {
  }

  getItemsPackagesBySearch(txt, category, district, town, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemsPackagesBySearch/' + txt + '/' + category + '/' + district + '/' + town + '/' + customerId);
  }

  getShopsBySearch(txt, category, district, town, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getShopsBySearch/' + txt + '/' + category + '/' + district + '/' + town + '/' + customerId);
  }

  getItemPackageSelected(itemId, type, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageSelected/' + itemId + '/' + type + '/' + customerId);
  }

  getFavItemPackages(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/customer/getFavItemPackages/' + customerId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getCustomerOrders(customerId, status, from, to): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/customer/getCustomerOrders/' + customerId + '/' + status + '/' + from + '/' + to, {headers: this.commonService.createAuthorizationHeader()});
  }

  setItemFavourite(customerId, itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/customer/setItemPackageFavourite/' + customerId + '/' + itemId, {headers: this.commonService.createAuthorizationHeader()});
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
