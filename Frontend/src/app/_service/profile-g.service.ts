import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProfileGService {

  profile = {
    profileId: '',
    returnUrl: '',
    breadCrumb: [],
    business: undefined
  };

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  getBusinessProfile(businessProfileId, needItems, customerId, type): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getBusinessProfile/' + businessProfileId + '/' + needItems + '/' + customerId + '/' + type);
  }

  getItemsBusinessProfile(businessProfileId, categoryId, customerId, type): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getItemsBusinessProfile/' + businessProfileId + '/' + categoryId + '/' + customerId + '/' + type);
  }

  followBusiness(customerId, businessProfileId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/customer/followBusiness/' + customerId + '/' + businessProfileId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addBusinessReview(businessReview): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'business_profile/customer/addBusinessReview', businessReview, {headers: this.commonService.createAuthorizationHeader()});
  }

  updateBusinessReview(businessReview, reviewId): Observable<any> {
    return this.http.put<any>(environment.backend_url + 'business_profile/customer/updateBusinessReview/' + reviewId, businessReview, {headers: this.commonService.createAuthorizationHeader()});
  }

  removeBusinessReview(reviewId): Observable<any> {
    return this.http.delete<any>(environment.backend_url + 'business_profile/customer/removeBusinessReview/' + reviewId, {headers: this.commonService.createAuthorizationHeader()});
  }

  addBusinessReviewResponse(businessReviewResponse): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'business_profile/customer/addBusinessReviewResponse', businessReviewResponse, {headers: this.commonService.createAuthorizationHeader()});
  }

  getBusinessReviews(businessId, customerId): Observable<any> {
    return this.http.get<any>(environment.backend_url + 'business_profile/getBusinessReviews/' + businessId + '/' + customerId);
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
      followed: false,
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
      itemCategories: [],
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
