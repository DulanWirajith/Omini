import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor() { }

  createAuthorizationHeader() {

    // if (localStorage.getItem('user') == null) {
    //   // this.loginService.logout();
    //   this.router.navigate(['/'])
    // }

    let headers: HttpHeaders = new HttpHeaders({
      // 'Content-Type': 'application/json',
      // 'Access-Control-Allow-Origin': 'http://192.168.1.4:8080',
      // 'Access-Control-Allow-Credentials': 'true',
      // 'Access-Control-Allow-Headers': 'Content-Type',
      // 'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE',
    });
    headers = headers.append('Authorization', localStorage.getItem('token'));
    // console.log(headers)
    return headers;
  }
}
