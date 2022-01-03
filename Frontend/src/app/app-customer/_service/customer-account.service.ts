import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";

@Injectable({
  providedIn: 'root'
})
export class CustomerAccountService {

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  getCustomerProfile(customerProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'customer_profile/getCustomerProfile/' + customerProfileId);
  }

  getBusinessCategories(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_category/getBusinessCategories');
  }

  getNewCustomerProfile() {
    return {
      customerProId: "",
      customerName: "",
      contactNumber: "",
      customerAddress: "",
      gender: "",
      dbayUser: {
        username: "",
        cPassword: "",
        password: "",
        passwordC: "",
        email: "",
        verificationCode: "",
        twoFactorAuth: false
      }
    }
  }
}
