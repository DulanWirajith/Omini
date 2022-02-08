import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {environment} from "../../../../../environments/environment";
import {ItemGService} from "../../../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ProfileGService} from "../../../../_service/profile-g.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-c-favourite',
  templateUrl: './c-favourite.component.html',
  styleUrls: ['./c-favourite.component.css']
})
export class CFavouriteComponent implements OnInit {

  items = [];
  packageItems = [];

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer,private profileService: ProfileGService, private router: Router) {
  }

  ngOnInit(): void {
    this.getFavourites();
  }

  getFavourites() {
    this.itemService.getFavItemPackages(JSON.parse(localStorage.getItem('user')).userId).subscribe((itemPackages) => {
      this.items = itemPackages.items;
      this.packageItems = itemPackages.itemPackages;
    })
  }

  setItemFavourite(itemPackage, index) {
    this.itemService.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, itemPackage.itemPackageId).subscribe((reply) => {
      if (!reply) {
        if (itemPackage.itemPackageType === 'Item') {
          this.items.splice(index, 1);
        } else if (itemPackage.itemPackageType === 'Package') {
          this.packageItems.splice(index, 1);
        }
      }
    })
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  routeToShop(profileId) {
    this.profileService.profile.profileId = profileId;
    this.profileService.profile.returnUrl = '/customer/header/customer_account/customer_favourite';
    this.router.navigate(['/shop/header/shop_view']);
  }
}
