import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CommonService} from "./common.service";
import {environment} from "../../environments/environment";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BusinessRegisterService {

  constructor(private http: HttpClient, private commonService: CommonService) {
  }
}
