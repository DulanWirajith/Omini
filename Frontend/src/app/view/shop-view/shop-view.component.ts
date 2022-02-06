import {Component, OnInit} from '@angular/core';
import {ProfileGService} from "../../_service/profile-g.service";
import {LoginService} from "../../_service/login.service";
import {environment} from "../../../environments/environment";
import {BusinessAccountService} from "../../app-shop/_service/business-account.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ItemService} from "../../app-customer/_service/item.service";
import {ItemGService} from "../../_service/item-g.service";

@Component({
  selector: 'app-shop-view',
  templateUrl: './shop-view.component.html',
  styleUrls: ['./shop-view.component.css']
})
export class ShopViewComponent implements OnInit {

  businessProfile;
  items = [];
  packageItems = [];

  constructor(private profileService: ProfileGService, private loginService: LoginService, private sanitizer: DomSanitizer, private itemServiceG: ItemGService) {
    this.businessProfile = profileService.getNewBusinessProfile();
  }

  ngOnInit(): void {
    this.getBusinessProfile()
  }

  getBusinessProfile() {
    this.profileService.getBusinessProfile(this.loginService.getUser().userId, true).subscribe((businessProfile) => {
      // this.user = this.loginService.getUser();
      // let businessProfile = this.loginService.getUser();
      //console.log(businessProfile)
      if (businessProfile !== null) {
        // console.log(businessProfile)
        this.businessProfile = businessProfile;
        this.items = businessProfile.itemPackage.items;
        this.packageItems = businessProfile.itemPackage.itemPackages;
      }
    })
  }

  getItemPackageSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined,
      cart: true
    });
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }
}
