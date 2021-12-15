import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {environment} from "../../environments/environment";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BusinessRegisterService {

  businessReg;
  step = new Subject<number>();

  constructor(private http: HttpClient, private commonService: CommonService) {
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

  getNewBR() {
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
        townId: ""
      },
      businessAreas: [],
      businessProfileCategories: [],
      user: {
        username: "",
        cPassword: "",
        password: "",
        passwordC: "",
        email: "",
        verificationCode: ""
      }
    }
  }
}
