import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "../../_service/common.service";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BusinessAccountService {

  businessProfile;
  step = new Subject<number>();
  breadCrumbSub = new Subject<string>();
  businessCategoriesSub = new Subject<any>();
  businessCategories = [];

  constructor(private http: HttpClient, private commonService: CommonService) {
    this.getBusinessCategoriesCom();
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

  getBusinessCategoriesCom() {
    this.getBusinessCategories().subscribe((businessCategories) => {
      this.businessCategoriesSub.next(businessCategories);
      this.businessCategories = businessCategories;
    })
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