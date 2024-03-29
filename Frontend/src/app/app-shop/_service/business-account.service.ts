import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import {LoginService} from "../../_service/login.service";

@Injectable({
  providedIn: 'root'
})
export class BusinessAccountService {

  businessProfile;
  step = new Subject<number>();
  breadCrumbSub = new Subject<string>();
  businessCategorySub = new Subject<any>();
  businessCategoriesSub = new Subject<any>();
  navBarSub = new Subject<any>();
  // businessTypeSub = new Subject<any>();
  businessCategories;
  businessCategory;

  itemOrders = [];

  constructor(private http: HttpClient, private commonService: CommonService, private loginService: LoginService) {
    // if (this.router.url !== '/login' && this.loginService.getUser().role !== 'B') {
    //   localStorage.clear();
    //   this.router.navigate(['']);
    // }
    // this.getBusinessCategoriesCom();
  }

  // setText(txt) {
  //   this.breadCrumbSub.next(txt)
  // }

  updateBusinessProfile(businessReg, businessProId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'business_profile/shop/updateBusinessProfile/' + businessProId, businessReg, {headers: this.commonService.createAuthorizationHeader()});
  }

  getBusinessProfile(businessProfileId, needItems, customerId, type): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getBusinessProfile/' + businessProfileId + '/' + needItems + '/' + customerId + '/' + type);
  }

  addBusinessProfile(businessReg): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'business_profile/addBusinessProfile', businessReg);
  }

  getBusinessCategories(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_category/getBusinessCategories');
  }

  getBusinessAreas(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_area/getBusinessAreas');
  }

  getTownsWIthDistrict(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getTownsWIthDistrict');
  }

  getCountries(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getCountries');
  }

  getDistricts(countryId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getDistricts/' + countryId);
  }

  getTowns(regionId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getTowns/' + regionId);
  }

  getNavBar(userId, businessCategory): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'dbay_user/getNavBar/' + userId + '/' + businessCategory + '/Business');
  }

  sendVerification(): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'dbay_user/sendVerification/' + JSON.parse(localStorage.getItem('br')).dbayUser.email);
  }

  getBusinessReviews(businessId, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getBusinessReviews/' + businessId + '/' + customerId);
  }

  getBusinessCategoriesCom() {
    // this.getBusinessCategories().subscribe((businessCategories) => {
    //   this.businessCategoriesSub.next(businessCategories);
    //   this.businessCategories = businessCategories;
    // })
  }

  getNewBusinessProfile() {
    return {
      businessProId: "",
      businessName: "",
      businessEmail: "",
      businessAddress: "",
      contactNumber1: "",
      contactNumber2: "",
      contactNumber3: "",
      businessRegistrationCode: "",
      businessOwner: "",
      businessOwnerNic: "",
      defaultBusiness: "",
      socialFb: "",
      socialInsta: "",
      socialTwitter: "",
      socialLinkedIn: "",
      country: "",
      region: "",
      rating1: 0,
      rating2: 0,
      town: {
        townId: "",
        region: {
          country: {
            name: ""
          }
        }
      },
      businessAreas: [],
      businessProfileCategories: [],
      dbayUser: {
        username: "",
        cPassword: "",
        password: "",
        passwordC: "",
        email: "",
        verificationCode: "",
        twoFactorAuth: false,
        userImgName: "",
        userImgType: "",
        thumbnail: false,
        dbayUserImgRaw: undefined
      }
    }
  }
}
