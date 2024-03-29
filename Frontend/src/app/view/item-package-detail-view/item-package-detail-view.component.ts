import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ItemGService} from "../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";
import {NgForm} from "@angular/forms";
import * as $ from "jquery";

@Component({
  selector: 'app-item-package-detail-view',
  templateUrl: './item-package-detail-view.component.html',
  styleUrls: ['./item-package-detail-view.component.css']
})
export class ItemPackageDetailViewComponent implements OnInit, OnDestroy {

  static lastComp: ItemPackageDetailViewComponent;

  itemPackageObj = {
    itemPackage: undefined,
    backBtn: undefined,
    cart: undefined
  };
  itemPackage;
  _album: [];
  review = false;
  itemPackageReview;
  itemPackageReviews = [];
  prevItemPackage;
  @ViewChild('reviewForm', {static: true}) public reviewForm: NgForm;

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private shopCartService: ShopCartService) {
    if (ItemPackageDetailViewComponent.lastComp === undefined) {
      this.itemPackage = this.getNewPackage();
      this.itemPackageReview = this.getNewItemPackageReview();
      this.itemService.itemPackageSub.observers = [];
      this.itemService.itemPackageSub.subscribe((itemPackageObj) => {
        // console.log(itemPackageObj)
        this.itemPackageObj = itemPackageObj;
        this.getPackageItemSelected(itemPackageObj.itemPackage);
      })
      $(document).on('click', '.item-package-viewer-g-btn', function () {
      });
    } else {
      return ItemPackageDetailViewComponent.lastComp;
    }

  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    ItemPackageDetailViewComponent.lastComp = this;
  }

  addToCart() {
    this.itemPackage.orderDetail.orderDetailType = this.itemPackage.itemPackageType;
    this.shopCartService.shopCartSub.next(this.itemPackage);
  }

  getPackageItemSelected(itemPackage) {
    // console.log(itemPackage)
    if (document.getElementById('package-back-btn') !== null) {
      document.getElementById('package-back-btn').click()
    }
    let customerId = '0';
    if (JSON.parse(localStorage.getItem('user')) !== null) {
      customerId = JSON.parse(localStorage.getItem('user')).userId;
    }
    this.itemService.getItemPackageSelected(itemPackage.itemPackageId, itemPackage.itemPackageType, customerId).subscribe((itemPackageObj) => {
      if (itemPackage.itemPackageType === 'Item') {
        this.itemPackage = itemPackageObj.item.itemPackage;
      } else if (itemPackage.itemPackageType === 'Package') {
        this.itemPackage = itemPackageObj.packageItem.itemPackage;
        this.itemPackage.packageItemItems = itemPackageObj.packageItem.packageItemItems;
      }
      // console.log(this.itemPackage)
      if (itemPackage.orderDetail !== undefined) {
        this.itemPackage.orderDetail.quantity = itemPackage.orderDetail.quantity
      }
      this.getAlbum(this.itemPackage);
    })
  }

  addItemPackageReview() {
    this.itemPackageReview.itemPackage = {
      itemPackageId: this.itemPackage.itemPackageId
    };
    // this.itemPackageReview.reviewType = 'ItemPackage';
    this.itemPackageReview.customerProfile.customerProId = this.getUser().customerProfile.customerProId;
    this.itemService.addItemPackageReview(this.itemPackageReview).subscribe((itemPackageReview) => {
      this.itemPackageReviewR.itemPackageReviews.push(itemPackageReview);
      if (this.itemPackageReviewR.itemPackageReviews.length > 0) {
        this.itemPackageReviewR.rating1 = 0;
        for (let review of this.itemPackageReviewR.itemPackageReviews) {
          this.itemPackageReviewR.rating1 += review.rating;
        }
        this.itemPackageReviewR.rating1 = this.itemPackageReviewR.rating1 / this.itemPackageReviewR.itemPackageReviews.length;
        this.itemPackageObj.itemPackage.rating1 = this.itemPackageReviewR.rating1;
      }
      this.reviewForm.resetForm();
      this.itemPackageReview = this.getNewItemPackageReview();
    })
  }

  updateItemPackageReview(itemPackageReview) {
    this.itemService.updateItemPackageReview(itemPackageReview, itemPackageReview.itemPackageReviewId).subscribe((itemPackageReviewR) => {
      itemPackageReview.description = itemPackageReviewR.description;
      itemPackageReview.rating = itemPackageReviewR.rating;
      itemPackageReview.tempRating = itemPackageReviewR.tempRating;
      itemPackageReview.editReview = false;
      if (this.itemPackageReviewR.itemPackageReviews.length > 0) {
        this.itemPackageReviewR.rating1 = 0;
        for (let review of this.itemPackageReviewR.itemPackageReviews) {
          this.itemPackageReviewR.rating1 += review.rating;
        }
        this.itemPackageReviewR.rating1 = this.itemPackageReviewR.rating1 / this.itemPackageReviewR.itemPackageReviews.length;
        this.itemPackageObj.itemPackage.rating1 = this.itemPackageReviewR.rating1;
      }
    })
  }

  removeItemPackageReview(reviewId, index) {
    this.itemService.removeItemPackageReview(reviewId).subscribe((reply) => {
      if (reply) {
        this.itemPackageReviewR.itemPackageReviews.splice(index, 1)
        if (this.itemPackageReviewR.itemPackageReviews.length > 0) {
          this.itemPackageReviewR.rating1 = 0;
          for (let review of this.itemPackageReviewR.itemPackageReviews) {
            this.itemPackageReviewR.rating1 += review.rating;
          }
          this.itemPackageReviewR.rating1 = this.itemPackageReviewR.rating1 / this.itemPackageReviewR.itemPackageReviews.length;
          this.itemPackageObj.itemPackage.rating1 = this.itemPackageReviewR.rating1;
        }
      }
    })
  }

  addItemReviewResponse(itemPackageReview, response) {
    // console.log(itemPackageReview)
    if (this.getUser() !== null && this.getUser().role === 'C') {
      let itemPackageReviewResponseId = '';
      if (itemPackageReview.responseByMe !== undefined) {
        itemPackageReviewResponseId = itemPackageReview.responseByMe.itemPackageReviewResponseId
      }
      let responseTemp = response;
      if (itemPackageReview.responseByMe !== undefined && itemPackageReview.responseByMe.response === response) {
        response = 'remove';
      }
      let itemReviewResponse = {
        itemPackageReviewResponseId: itemPackageReviewResponseId,
        response: response,
        itemPackageReview: {itemPackageReviewId: itemPackageReview.itemPackageReviewId},
        customerProfile: {customerProId: JSON.parse(localStorage.getItem('user')).customerProfile.customerProId}
      };
      // console.log(itemReviewResponse)
      this.itemService.addItemPackageResponse(itemReviewResponse).subscribe((itemReviewResponseObj) => {
        if (itemReviewResponseObj.response === 'like') {
          if (itemPackageReview.responseByMe !== undefined) {
            itemPackageReview.dislikeCount--;
          }
          itemPackageReview.likeCount++;
          itemPackageReview.responseByMe = itemReviewResponseObj;
        } else if (itemReviewResponseObj.response === 'dislike') {
          itemPackageReview.dislikeCount++;
          if (itemPackageReview.responseByMe !== undefined) {
            itemPackageReview.likeCount--;
          }
          itemPackageReview.responseByMe = itemReviewResponseObj;
        } else if (itemReviewResponseObj.response === 'remove') {
          if (responseTemp === 'like') {
            // if (itemReview.likeCount > 0) {
            itemPackageReview.likeCount--;
            // }
          } else if (responseTemp === 'dislike') {
            // if (itemReview.dislikeCount > 0) {
            itemPackageReview.dislikeCount--;
            // }
          }
          itemPackageReview.responseByMe = undefined;
        }
      })
    }
  }

  itemPackageReviewR = {
    rating1: 0,
    rating2: 0,
    itemPackageReviews: []
  };

  getItemPackageReviews() {
    this.review = true;
    let customerId = '0';
    if (JSON.parse(localStorage.getItem('user')) !== null) {
      customerId = JSON.parse(localStorage.getItem('user')).userId;
    }
    this.itemService.getItemPackageReviews(this.itemPackage.itemPackageId, customerId).subscribe((itemPackageReview) => {
      this.itemPackageReviewR = itemPackageReview;
      // console.log(this.itemPackageReviews)
    })
  }

  getItemPackageSelected(prevItemPackage, itemPackage) {
    // this.itemService.itemPackageSub.next({
    //   itemPackage: itemPackage,
    //   backBtn: 'item-package-viewer-g'
    // });
    this.itemPackage = itemPackage;
    this.prevItemPackage = prevItemPackage;
    this.getAlbum(this.itemPackage);
    this.itemPackageObj.backBtn = true;
  }

  setItemFavourite(itemPackage) {
    this.itemService.setItemFavourite(JSON.parse(localStorage.getItem('user')).userId, itemPackage.itemPackageId).subscribe((reply) => {
      itemPackage.favourite = reply;
    })
  }

  goBack() {
    this.itemPackage = this.prevItemPackage;
    this.itemPackageObj.backBtn = undefined;
    this.getAlbum(this.prevItemPackage);
  }

  open(index: number): void {
    // open lightbox
    this.lightbox.open(this._album, index);
  }

  close(): void {
    // close lightbox programmatically
    this.lightbox.close();
  }

  getAlbum(imageList) {
    let promises = [];
    imageList.itemPackageImages.forEach(element => {
      promises.push(this.setAlbum(element));
    });
    Promise.all(promises).then((result: []) => {
      this._album = result;
      //console.log(this._album);
    });
  }

  setAlbum(img) {
    return new Promise((resolve, reject) => {
      resolve({
        src: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.imageName),
        caption: img.itemPackageImageId,
        thumb: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.imageName)
      })
    });
  }

  getAlbumSrc(imgNo) {
    let albumImgSrc = '';
    if (this._album[imgNo] !== undefined) {
      albumImgSrc = this._album[imgNo]['thumb'];
    }
    return albumImgSrc;
  }

  getImagePackageSrc(itemPackageImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImg.itemPackageImgName);
  }

  getImageSrc(itemPackageImage) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.imageName);
  }

  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getUser() {
    // console.log(JSON.parse(localStorage.getItem('user')).role)
    return JSON.parse(localStorage.getItem('user'));
  }

  getNewPackage() {
    return {
      itemPackageId: "",
      name: "",
      description: "",
      price: "",
      quantity: 1,
      makeToOrder: false,
      discount: "",
      discountType: "None",
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      itemPackageType: '',
      isNewPackage: false,
      isUpdatePackage: false,
      packageItemItems: [],
      items: [],
      itemPackageItemPackageFeatures: [],
      itemPackageItemPackageFeature: "",
      itemPkgImgs: [],
      itemPackageImages: [],
      tempBusinessCategory: undefined,
      tempItems: [],
      tempItemFeatures: []
    }
  }

  getNewItemPackageReview() {
    return {
      itemReviewId: '',
      description: '',
      rating: '',
      likeCount: 0,
      dislikeCount: 0,
      postedDate: '',
      itemPackage: undefined,
      customerProfile: {
        customerProId: ''
      }
    }
  }
}
