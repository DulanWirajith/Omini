import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {AppCustomerModule} from "../app-customer.module";

@Injectable({
  providedIn: 'root'
})
export class ShopCartService {

  shopCartSub = new Subject<any>();
  shopCart = [];
  shopCartItemsSub = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService) {

  }

  // getNewShopCart(){
  //   return {
  //     shop: "",
  //     items: []
  //   }
  // }
}
