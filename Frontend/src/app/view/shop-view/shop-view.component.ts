import {Component, OnInit, ViewChild} from '@angular/core';
import {ProfileGService} from "../../_service/profile-g.service";
import {LoginService} from "../../_service/login.service";
import {environment} from "../../../environments/environment";
import {BusinessAccountService} from "../../app-shop/_service/business-account.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ItemService} from "../../app-customer/_service/item.service";
import {ItemGService} from "../../_service/item-g.service";
import {NgForm} from "@angular/forms";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";

@Component({
  selector: 'app-shop-view',
  templateUrl: './shop-view.component.html',
  styleUrls: ['./shop-view.component.css']
})
export class ShopViewComponent implements OnInit {

  businessProfile;
  items = [];
  packageItems = [];
  selectedType = {
    businessCategory: {
      businessCategoryId: ''
    }
  };
  selectedCategory = {
    itemCategoryId: 0
  };
  profileId;
  businessCategories = [];

  constructor(private profileService: ProfileGService, private loginService: LoginService, private sanitizer: DomSanitizer, private itemServiceG: ItemGService, private shopCartService: ShopCartService) {
    this.businessProfile = profileService.getNewBusinessProfile();
    this.businessReview = this.getNewBusinessReview();
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
    // this.profileService.shopProfileSub.subscribe((profileId) => {
    //   this.profileId = profileId;
    // })
  }

  ngOnInit(): void {
    this.getBusinessProfile();
  }

  getBusinessProfile() {
    // console.log(this.profileService.profile.profileId)
    if (this.profileService.profile.profileId === '') {
      this.profileService.profile.profileId = this.getUser().userId;
    }
    this.profileService.getBusinessProfile(this.profileService.profile.profileId, true, this.getUser().userId, this.getUser().role).subscribe((businessProfile) => {
      // this.user = this.loginService.getUser();
      // let businessProfile = this.loginService.getUser();
      //console.log(businessProfile)
      if (businessProfile !== null) {
        // console.log(businessProfile.itemPackage.items)
        this.businessProfile = businessProfile;
        this.selectedType = businessProfile.defaultBusiness;
        this.items = businessProfile.itemPackage.items;
        this.packageItems = businessProfile.itemPackage.itemPackages;
        this.getBusinessReviews();
        this.setShopCart(this.shopCartService.shopCart);
      }
    })
  }

  getItemsBusinessProfile() {
    // console.log(this.selectedType)
    // this.selectedType.businessCategoryId = categoryId;
    this.profileService.getItemsBusinessProfile(this.profileService.profile.profileId, this.selectedType.businessCategory.businessCategoryId, this.getUser().userId, this.getUser().role).subscribe((businessProfile) => {
      // this.user = this.loginService.getUser();
      // let businessProfile = this.loginService.getUser();
      //console.log(businessProfile)
      if (businessProfile !== null) {
        // console.log(businessProfile)
        // this.businessProfile = businessProfile;
        this.items = businessProfile.itemPackage.items;
        this.packageItems = businessProfile.itemPackage.itemPackages;
        this.setShopCart(this.shopCartService.shopCart);
      }
    })
  }

  getItemsItemCategory(itemCategoryId) {
    this.selectedCategory.itemCategoryId = itemCategoryId
    if (itemCategoryId === 0) {
      this.items = this.businessProfile.itemPackage.items;
    } else {
      this.items = this.businessProfile.itemPackage.items;
      let items = [];
      for (let item of this.items) {
        if (item.itemCategory !== undefined && item.itemCategory.itemCategoryId === itemCategoryId) {
          items.push(item)
        }
      }
      this.items = items;
    }
  }

  addToCart(item, orderDetailType) {
    // console.log(item)
    //console.log(this.shopCartService.shopCartSub)
    item.orderDetail.orderDetailType = orderDetailType;
    this.shopCartService.shopCartSub.next(item);
  }

  // calcDiscount(itemPackage) {
  //   if (itemPackage.discountType === 'Cash') {
  //     return itemPackage.price - itemPackage.discount;
  //   } else if (itemPackage.discountType === 'Percentage') {
  //     return itemPackage.price * ((100 - itemPackage.discount) / 100);
  //   }
  //   return '';
  // }

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

  followBusiness(businessProfile) {
    this.profileService.followBusiness(JSON.parse(localStorage.getItem('user')).userId, businessProfile.businessProId).subscribe((followed) => {
      businessProfile.followed = followed;
      if (this.profileService.profile.business !== undefined) {
        this.profileService.profile.business.followed = followed;
      }
    })
  }

  setItemFavourite(itemPackage) {
    this.itemServiceG.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, itemPackage.itemPackageId).subscribe((reply) => {
      itemPackage.favourite = reply;
    })
  }

  getItemPackageSelected(itemPackage) {
    let cart = undefined;
    if (this.getUser() !== null && this.getUser().role === 'C') {
      cart = true;
    }
    this.itemServiceG.itemPackageSub.next({
      itemPackage: itemPackage,
      backBtn: undefined,
      cart: cart
    });
  }

  getImageSrc(itemPackageImage) {
    // console.log(itemPackageImage)
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  getUser() {
    // console.log(JSON.parse(localStorage.getItem('user')).role)
    return JSON.parse(localStorage.getItem('user'));
  }

  getReturnUrl() {
    return this.profileService.profile.returnUrl;
  }

  //

  review = false;
  businessReview;
  businessReviews = [];
  // prevItemPackage;
  @ViewChild('reviewForm', {static: true}) public reviewForm: NgForm;

  addBusinessReview() {
    this.businessReview.businessProfile = {
      businessProId: this.businessProfile.businessProId
    };
    // this.itemPackageReview.reviewType = 'ItemPackage';
    this.businessReview.customerProfile.customerProId = JSON.parse(localStorage.getItem('user')).customerProfile.customerProId;
    this.profileService.addBusinessReview(this.businessReview).subscribe((businessReview) => {
      this.businessReviews.push(businessReview);
      this.reviewForm.resetForm()
      this.businessReview = this.getNewBusinessReview();
    })
  }

  addBusinessReviewResponse(businessReview, response) {
    // console.log(itemPackageReview)
    if (this.getUser().role === 'C') {
      let businessReviewResponseId = '';
      if (businessReview.responseByMe !== undefined) {
        businessReviewResponseId = businessReview.responseByMe.businessReviewResponseId
      }
      let responseTemp = response;
      if (businessReview.responseByMe !== undefined && businessReview.responseByMe.response === response) {
        response = 'remove';
      }
      let itemReviewResponse = {
        businessReviewResponseId: businessReviewResponseId,
        response: response,
        businessReview: {businessReviewId: businessReview.businessReviewId},
        customerProfile: {customerProId: JSON.parse(localStorage.getItem('user')).customerProfile.customerProId}
      };
      // console.log(itemReviewResponse)
      this.profileService.addBusinessReviewResponse(itemReviewResponse).subscribe((itemReviewResponseObj) => {
        if (itemReviewResponseObj.response === 'like') {
          if (businessReview.responseByMe !== undefined) {
            businessReview.dislikeCount--;
          }
          businessReview.likeCount++;
          businessReview.responseByMe = itemReviewResponseObj;
        } else if (itemReviewResponseObj.response === 'dislike') {
          businessReview.dislikeCount++;
          if (businessReview.responseByMe !== undefined) {
            businessReview.likeCount--;
          }
          businessReview.responseByMe = itemReviewResponseObj;
        } else if (itemReviewResponseObj.response === 'remove') {
          if (responseTemp === 'like') {
            // if (itemReview.likeCount > 0) {
            businessReview.likeCount--;
            // }
          } else if (responseTemp === 'dislike') {
            // if (itemReview.dislikeCount > 0) {
            businessReview.dislikeCount--;
            // }
          }
          businessReview.responseByMe = undefined;
        }
      })
    }
  }

  getBusinessReviews() {
    this.review = true;
    this.profileService.getBusinessReviews(this.businessProfile.businessProId, JSON.parse(localStorage.getItem('user')).userId).subscribe((businessReviews) => {
      this.businessReviews = businessReviews;
      // console.log(this.itemPackageReviews)
    })
  }

  getNewBusinessReview() {
    return {
      businessReviewId: '',
      description: '',
      rating: '',
      likeCount: 0,
      dislikeCount: 0,
      postedDate: '',
      businessProfile: undefined,
      customerProfile: {
        customerProId: ''
      }
    }
  }
}
