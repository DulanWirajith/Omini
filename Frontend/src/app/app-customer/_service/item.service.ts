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

  getItemSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
  }

  getItemPackageSelected(itemPackageId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'package/getItemPackageSelected/' + itemPackageId);
  }

  getNewItem() {
    return {
      itemId: "",
      orderDetailId: '',
      itemTitle: "",
      itemQty: 1,
      itemCount: 0,
      itemDiscount: "",
      itemDiscountType: "None",
      itemPrice: "",
      itemDescription: "",
      itemImgs: [],
      itemImgsRaw: [],
      itemAvailable: false,
      businessProfileCategory: {
        businessProfile: {
          businessProfile: undefined
        },
        businessCategory: undefined
      },
      itemItemFeatures: [],
      isNewItem: false,
      isUpdateItem: false,
      orderDetail: undefined
    }
  }
}
