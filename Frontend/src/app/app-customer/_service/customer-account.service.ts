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
    // console.log(this.loginService.getUser())
    // if (this.router.url !== '/login' && this.loginService.getUser().role !== 'C') {
    //   localStorage.clear();
    //   this.router.navigate(['']);
    // }
  }

  addCustomerProfile(customerReg): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'customer_profile/addCustomerProfile', customerReg);
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

  getFollowedBusinesses(customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getFollowedBusinesses/' + customerId);
  }

  followBusiness(customerId, businessProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/followBusiness/' + customerId + '/' + businessProfileId);
  }

  sendVerification(): Observable<any> {
    // console.log(JSON.parse(localStorage.getItem('cr')).dbayUser)
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
