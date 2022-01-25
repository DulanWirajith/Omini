import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {Router} from "@angular/router";
import {ShopCartService} from "../app-customer/_service/shop-cart.service";
import {ShopCartComponent} from "../app-customer/view/shop-cart/shop-cart.component";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  user;
  loggedIn = new Subject<any>();

  constructor(private http: HttpClient, private commonService: CommonService, private router: Router) {
    // console.log(this.router.url)
    // if (this.router.url !== '/login' && this.getUser() === null) {
    //   localStorage.clear();
    //   this.router.navigate(['']);
    // }
  }

  authenticate(user): Observable<any> {
    return this.http.post<any>(environment.backend_url + 'dbay_user/authenticate', user);
  }

  getUser() {
    //console.log(this.user)
    if (this.user === undefined || this.user === null) {
      this.user = JSON.parse(localStorage.getItem('user'));
    }
    return this.user;
  }

  signOut() {
    localStorage.clear();
    this.user = null;
    // this.loggedIn.next(0);
    ShopCartComponent.lastComp = undefined;
    this.router.navigate(['/login']);
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
      twoFactorAuth: '',
      countPendingOrders: 0
    }
  }
}
