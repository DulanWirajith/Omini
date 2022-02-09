import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {Router} from "@angular/router";
import {LoginService} from "../../_service/login.service";

@Injectable({
  providedIn: 'root'
})
export class CustomerAccountService {

  step = new Subject<number>();

  constructor(private http: HttpClient, private commonService: CommonService, private router: Router, private loginService: LoginService) {

  }

  addCustomerProfile(customerReg): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'customer_profile/addCustomerProfile', customerReg);
  }

  updateCustomerProfile(customerReg, customerProId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'customer_profile/customer/updateCustomerProfile/' + customerProId, customerReg, {headers: this.commonService.createAuthorizationHeader()});
  }

  getCustomerProfile(customerProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'customer_profile/customer/getCustomerProfile/' + customerProfileId, {headers: this.commonService.createAuthorizationHeader()});
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

  getFollowedBusinesses(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/customer/getFollowedBusinesses/' + customerId, {headers: this.commonService.createAuthorizationHeader()});
  }

  followBusiness(customerId, businessProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/customer/followBusiness/' + customerId + '/' + businessProfileId, {headers: this.commonService.createAuthorizationHeader()});
  }

  sendVerification(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'dbay_user/sendVerification/' + JSON.parse(localStorage.getItem('cr')).dbayUser.email);
  }

  getNewCustomerProfile() {
    return {
      customerProId: "",
      firstName: "",
      lastName: "",
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
