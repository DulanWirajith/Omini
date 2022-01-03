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
  searchedItems = [];

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  getItemsBySearch(txt, category): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemsBySearch/' + txt + '/' + category);
  }

  getItemSelected(itemId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'item/getItemSelected/' + itemId);
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
      itemImgsRaw: [],
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
}
