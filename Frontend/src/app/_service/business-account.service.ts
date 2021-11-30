import {Injectable} from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BusinessAccountService {

  breadCrumbSub = new Subject<string>();

  constructor() {
  }

  setText(txt) {
    this.breadCrumbSub.next(txt)
  }
}
