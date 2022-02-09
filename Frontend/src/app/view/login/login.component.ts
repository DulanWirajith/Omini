import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../_service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user;

  constructor(private loginService: LoginService, private router: Router) {
    this.user = loginService.getNewUser();
  }

  ngOnInit(): void {
  }

  authenticate() {
    this.loginService.authenticate(this.user).subscribe((user) => {
      // console.log(user)
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('token', 'Bearer ' + user.token);
      // this.loginService.loggedIn.next(1);
      if (user.role === 'B') {
        this.router.navigate(['/shop/header/business_account/ba_monitor'])
      } else if (user.role === 'C') {
        this.router.navigate(['/customer/header/item_shop_search'])
      }
    })
  }
}
