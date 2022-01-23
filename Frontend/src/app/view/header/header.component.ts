import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../_service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) {
    // router.subscribe((val) => {
    //   // see also
    //   console.log(this.router.url)
    // });
  }

  ngOnInit(): void {
    // console.log(this.router.url)
  }

  getUser() {
    return this.loginService.getUser();
  }

  signOut() {
    this.loginService.signOut();
  }

  getCurUrl() {
    return this.router.url;
  }
}
