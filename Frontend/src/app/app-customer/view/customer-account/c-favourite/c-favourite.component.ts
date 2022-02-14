import {Component, OnInit} from '@angular/core';
import {ItemService} from "../../../_service/item.service";
import {environment} from "../../../../../environments/environment";
import {ItemGService} from "../../../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ProfileGService} from "../../../../_service/profile-g.service";
import {Router} from "@angular/router";
import {ShopCartService} from "../../../_service/shop-cart.service";

@Component({
  selector: 'app-c-favourite',
  templateUrl: './c-favourite.component.html',
  styleUrls: ['./c-favourite.component.css']
})
export class CFavouriteComponent implements OnInit {

  items = [];
  packageItems = [];

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer,private profileService: ProfileGService, private router: Router, private shopCartService: ShopCartService,private itemServiceG: ItemGService) {
    this.shopCartService.shopCartItemsSub.subscribe((item) => {
      // console.log(item)
      // console.log(this.items)
      let itemObj: any = this.items.find(itemObj => {
        return itemObj.itemPackageId === item.itemPackageId
      })
      if (itemObj !== undefined) {
        // console.log(itemObj)
        itemObj.quantity = item.quantity;
        itemObj.orderDetail.quantity = item.orderDetail.quantity;
      }
      let itemPackageObj: any = this.packageItems.find(itemPackageObj => {
        return itemPackageObj.itemPackageId === item.itemPackageId
      })
      if (itemPackageObj !== undefined) {
        itemPackageObj.quantity = item.quantity;
        itemPackageObj.orderDetail.quantity = item.orderDetail.quantity;
      }
    })
  }

  ngOnInit(): void {
    this.getFavourites();
  }

  getFavourites() {
    this.itemService.getFavItemPackages(JSON.parse(localStorage.getItem('user')).userId).subscribe((itemPackages) => {
      this.items = itemPackages.items;
      this.packageItems = itemPackages.itemPackages;
      this.setShopCart(this.shopCartService.shopCart);
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
    this.router.navigate(['/shop/header/shop_view'], {queryParams: {id: this.profileService.profile.profileId}})
  }

  addToCart(item, orderDetailType) {
    // console.log(item)
    //console.log(this.shopCartService.shopCartSub)
    item.orderDetail.orderDetailType = orderDetailType;
    this.shopCartService.shopCartSub.next(item);
  }

  setShopCart(shopCart) {
    // console.log(shopCart)
    for (let shop of shopCart) {
      for (let item of shop.items) {
        let orderDetail = item.orderDetail;
        // console.log(orderDetail)
        if (orderDetail.orderDetailType === 'Item') {
          let itemObj: any = this.items.find(itemObj => {
            return itemObj.itemPackageId === orderDetail.itemPackage.itemPackageId
          })
          if (itemObj !== undefined) {
            itemObj.orderDetail.quantity = orderDetail.quantity;
          }
        } else if (orderDetail.orderDetailType === 'Package') {
          let itemPackageObj: any = this.packageItems.find(itemPackageObj => {
            return itemPackageObj.itemPackageId === orderDetail.itemPackage.itemPackageId
          })
          if (itemPackageObj !== undefined) {
            // itemObj.quantity = item.quantity;
            itemPackageObj.orderDetail.quantity = orderDetail.quantity;
          }
        }
      }
    }
  }

  getItemPackageSelected(itemPackage) {
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined,
      cart: true
    });
  }
}
