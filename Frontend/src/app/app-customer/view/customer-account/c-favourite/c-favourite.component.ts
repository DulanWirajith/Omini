import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {environment} from "../../../../../environments/environment";
import {ItemGService} from "../../../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-c-favourite',
  templateUrl: './c-favourite.component.html',
  styleUrls: ['./c-favourite.component.css']
})
export class CFavouriteComponent implements OnInit {

  items = [];
  packageItems = [];

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer) {
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
}
