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

  addCategory(itemsCategory): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'category/addCategory', itemsCategory);
  }

  updateCategory(itemsCategory): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'category/updateCategory/' + itemsCategory.itemCategoryId, itemsCategory);
  }

  addPackage(itemsPackage): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'package/addPackage', itemsPackage);
  }

  updatePackage(itemsPackage, itemPackageId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'package/updatePackage/' + itemPackageId, itemsPackage);
  }

  getItemFeatures(businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_feature/getItemFeatures/' + businessCategoryId);
  }

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

  getItemPackagesOrdered(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getItemPackagesOrdered/' + businessProfileId + '/' + businessCategoryId);
  }

  setItemAvailable(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/setItemAvailable/' + itemId);
  }

  getItemSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
  }

  getItemCategorySelected(categoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'category/getItemCategorySelected/' + categoryId);
  }

  getItemPackageSelected(itemPackageId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getItemPackageSelected/' + itemPackageId);
  }

  getItemOrders(businessProfileId, businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_order/getItemOrders/' + businessProfileId + '/' + businessCategoryId);
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
      name: "",
      quantity: 1,
      itemCount: 0,
      discount: "",
      discountType: "None",
      price: "",
      description: "",
      itemImgs: [],
      itemImgsRaw: [],
      available: false,
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      itemItemFeatures: [],
      isNewItem: false,
      isUpdateItem: false,
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

  getNewPackage() {
    return {
      itemPackageId: "",
      name: "",
      description: "",
      price: "",
      discount: "",
      discountType: "None",
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      isNewPackage: false,
      isUpdatePackage: false,
      itemItemPackages: [],
      items: [],
      itemPackageItemPackageFeatures: [],
      itemPackageItemPackageFeature: "",
      itemPkgImgs: [],
      itemPackageImgs: [],
      tempBusinessCategory: undefined,
      tempItems: [],
      tempItemFeatures: []
    }
  }
}
