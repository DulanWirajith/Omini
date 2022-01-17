import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";

@Injectable({
  providedIn: 'root'
})
export class CustomerAccountService {

  step = new Subject<number>();

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  updateCustomerProfile(customerReg, customerProId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'customer_profile/updateCustomerProfile/' + customerProId, customerReg);
  }

  getCustomerProfile(customerProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'customer_profile/getCustomerProfile/' + customerProfileId);
  }

  getBusinessCategories(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_category/getBusinessCategories');
  }

  getCountries(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getCountries');
  }

  getDistricts(countryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getDistricts/' + countryId);
  }

  getTowns(districtId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getTowns/' + districtId);
  }

  getNewCustomerProfile() {
    return {
      customerProId: "",
      customerName: "",
      contactNumber1: "",
      contactNumber2: "",
      customerAddress: "",
      gender: "",
      country: "",
      district: "",
      town: {
        townId: "",
        district: {
          country: {
            name: ""
          }
        }
      },
      dbayUser: {
        username: "",
        cPassword: "",
        password: "",
        passwordC: "",
        email: "",
        verificationCode: "",
        twoFactorAuth: false,
        dbayUserImgs: [],
        dbayUserImgsRaw: []
      }
    }
  }
}
