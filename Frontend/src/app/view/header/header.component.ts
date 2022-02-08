import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../_service/login.service";
import {Router} from "@angular/router";
import {ProfileGService} from "../../_service/profile-g.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router, private profileService: ProfileGService) {
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

  routeToShop() {
    this.profileService.profileId = this.loginService.getUser().userId;
    this.router.navigate(['/shop/header/shop_view'])
  }
}
