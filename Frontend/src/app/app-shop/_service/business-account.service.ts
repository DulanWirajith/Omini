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

  constructor(private http: HttpClient, private commonService: CommonService, private router: Router, private loginService: LoginService) {
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
    return this.http.put<any>(environment.backend_url + 'business_profile/updateBusinessProfile/' + businessProId, businessReg);
  }

  getBusinessProfile(businessProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getBusinessProfile/' + businessProfileId);
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

  getTowns(districtId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'town/getTowns/' + districtId);
  }

  getNavBar(userId, businessCategory): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'dbay_user/getNavBar/' + userId + '/' + businessCategory + '/Business');
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
      district: "",
      town: {
        townId: "",
        district: {
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
        dbayUserImgs: [],
        dbayUserImgsRaw: []
      }
    }
  }
}
