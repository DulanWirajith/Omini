import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  itemSub = new Subject<any>();
  itemFeaturesSub = new Subject<any>();
  packageItemSub = new Subject<any>();
  packageItemFeaturesSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  addItem(item): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item/shop/addItem', item, {headers: this.commonService.createAuthorizationHeader()});
  }

  updateItem(item, itemId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'item/shop/updateItem/' + itemId, item, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeItem(itemId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'item/shop/removeItem/' + itemId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addCategory(itemsCategory): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'category/shop/addCategory', itemsCategory, {headers: this.commonService.createAuthorizationHeader()});
  }

  updateCategory(itemsCategory): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'category/shop/updateCategory/' + itemsCategory.itemCategoryId, itemsCategory, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeCategory(categoryId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'category/shop/removeCategory/' + categoryId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addPackage(itemsPackage): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'package/shop/addPackage', itemsPackage, {headers: this.commonService.createAuthorizationHeader()});
  }

  updatePackage(itemsPackage, itemPackageId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'package/shop/updatePackage/' + itemPackageId, itemsPackage, {headers: this.commonService.createAuthorizationHeader()});
  }

  removePackage(itemsPackageId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'package/shop/removePackage/' + itemsPackageId, {headers: this.commonService.createAuthorizationHeader()});
  }

  // getItemFeatures(businessCategoryId): Observable<any> {
  //   return this.http.get<any>(environment.backend_url + 'item_feature/getItemFeatures/' + businessCategoryId);
  // }

  getItemPackageFeatures(businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package_feature/shop/getItemPackageFeatures/' + businessCategoryId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemsBusinessCategory(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/shop/getItemsBusinessCategory/' + businessProfileId + '/' + businessCategoryId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemsOrdered(businessProfileId, businessCategoryId, start, limit): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/shop/getItemsOrdered/' + businessProfileId + '/' + businessCategoryId + '/' + start + '/' + limit, {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemCategoriesOrdered(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'category/shop/getItemCategoriesOrdered/' + businessProfileId + '/' + businessCategoryId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getPackageItemsOrdered(businessProfileId, businessCategoryId, start, limit): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/shop/getPackageItemsOrdered/' + businessProfileId + '/' + businessCategoryId + '/' + start + '/' + limit, {headers: this.commonService.createAuthorizationHeader()});
  }

  setItemPackageAvailable(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/shop/setItemPackageAvailable/' + itemId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemPackageSelected(itemId, type): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageSelected/' + itemId + '/' + type + '/0', {headers: this.commonService.createAuthorizationHeader()});
  }

  getItemPackageReviews(itemId, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/getItemPackageReviews/' + itemId + '/' + customerId);
  }

  // getItemSelected(itemId): Observable<any> {
  //   return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
  // }

  getItemCategorySelected(categoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'category/shop/getItemCategorySelected/' + categoryId, {headers: this.commonService.createAuthorizationHeader()});
  }

  // getPackageItemSelected(itemPackageId): Observable<any> {
  //   return this.http.get<any>(environment.backend_url + 'package/getPackageItemSelected/' + itemPackageId);
  // }

  getItemOrders(businessProfileId, businessCategoryId, status, from, to): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/shop/getItemOrders/' + businessProfileId + '/' + businessCategoryId + '/' + status + '/' + from + '/' + to, {headers: this.commonService.createAuthorizationHeader()});
  }

  changeOrderStatus(orderId, businessProfileId, businessCategoryId, status): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/shop/changeOrderStatus/' + orderId + '/' + businessProfileId + '/' + businessCategoryId + '/' + status, {headers: this.commonService.createAuthorizationHeader()});
  }

  acceptItem(orderDetailId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/shop/acceptItem/' + orderDetailId, {headers: this.commonService.createAuthorizationHeader()});
  }

  getNewItem() {
    return {
      itemId: "",
      itemPackage: this.getNewItemPackage(),
      isNewItem: false,
      isUpdateItem: false
    }
  }

  getNewPackage() {
    return {
      packageItemId: "",
      itemPackage: this.getNewItemPackage(),
      isNewPackage: false,
      isUpdatePackage: false,
      packageItemItems: []
    }
  }

  getNewItemPackage() {
    return {
      itemPackageId: "",
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
      available: false,
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      itemPackageItemPackageFeatures: []
    }
  }

  getNewCategory() {
    return {
      itemCategoryId: "",
      name: "",
      description: "",
      confirmed: "",
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      items: [],
      isNewCategory: false,
      isUpdateCategory: false,
      tempBusinessCategory: undefined,
      tempItems: []
    }
  }

}
