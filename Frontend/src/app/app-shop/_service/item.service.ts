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

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  addItem(item): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'item/addItem', item);
  }

  updateItem(item, itemId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'item/updateItem/' + itemId, item);
  }

  removeItem(itemId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'item/removeItem/' + itemId);
  }

  addCategory(itemsCategory): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'category/addCategory', itemsCategory);
  }

  updateCategory(itemsCategory): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'category/updateCategory/' + itemsCategory.itemCategoryId, itemsCategory);
  }

  removeCategory(categoryId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'category/removeCategory/' + categoryId);
  }

  addPackage(itemsPackage): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'package/addPackage', itemsPackage);
  }

  updatePackage(itemsPackage, itemPackageId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'package/updatePackage/' + itemPackageId, itemsPackage);
  }

  removePackage(itemsPackageId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'package/removePackage/' + itemsPackageId);
  }

  // getItemFeatures(businessCategoryId): Observable<any> {
  //   return this.http.get<any>(environment.backend_url + 'item_feature/getItemFeatures/' + businessCategoryId);
  // }

  getItemPackageFeatures(businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package_feature/getItemPackageFeatures/' + businessCategoryId);
  }

  getItemsBusinessCategory(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemsBusinessCategory/' + businessProfileId + '/' + businessCategoryId);
  }

  getItemsOrdered(businessProfileId, businessCategoryId, start, limit): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemsOrdered/' + businessProfileId + '/' + businessCategoryId + '/' + start + '/' + limit);
  }

  getItemCategoriesOrdered(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'category/getItemCategoriesOrdered/' + businessProfileId + '/' + businessCategoryId);
  }

  getPackageItemsOrdered(businessProfileId, businessCategoryId, start, limit): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getPackageItemsOrdered/' + businessProfileId + '/' + businessCategoryId + '/' + start + '/' + limit);
  }

  setItemPackageAvailable(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_package/setItemPackageAvailable/' + itemId);
  }

  getItemSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
  }

  getItemCategorySelected(categoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'category/getItemCategorySelected/' + categoryId);
  }

  getPackageItemSelected(itemPackageId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getPackageItemSelected/' + itemPackageId);
  }

  getItemOrders(businessProfileId, businessCategoryId, status, from, to): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/getItemOrders/' + businessProfileId + '/' + businessCategoryId + '/' + status + '/' + from + '/' + to);
  }

  changeOrderStatus(orderId, businessProfileId, businessCategoryId, status): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/changeOrderStatus/' + orderId + '/' + businessProfileId + '/' + businessCategoryId + '/' + status);
  }

  acceptItem(orderDetailId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/acceptItem/' + orderDetailId);
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
