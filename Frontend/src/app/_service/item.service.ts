import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";

@Injectable({
  providedIn: 'root'
})
export class ItemService {

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

  getItemFeatures(businessCategoryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item_feature/getItemFeatures/' + businessCategoryId);
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

  getNewItem() {
    return {
      itemId: "",
      itemTitle: "",
      itemQty: 1,
      itemDiscount: "",
      itemDiscountType: "None",
      itemPrice: "",
      itemDescription: "",
      itemImgs: [],
      itemAvailable: false,
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
      itemPkgImgs: [],
      itemPackageImgs: [],
      tempBusinessCategory: undefined,
      tempItems: []
    }
  }
}
