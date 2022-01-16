import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  user;

  constructor(private http: HttpClient, private commonService: CommonService) {
  }

  authenticate(user): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'dbay_user/authenticate', user);
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  getNewUser() {
    return {
      userId: '',
      username: '',
      type: '',
      password: '',
      verificationCode: '',
      role: '',
      email: '',
      available: false,
      confirmed: false,
      twoFactorAuth: ''
    }
  }
}
