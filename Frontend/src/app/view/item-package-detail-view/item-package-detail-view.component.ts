import {Component, OnInit, ViewChild} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ItemGService} from "../../_service/item-g.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-item-package-detail-view',
  templateUrl: './item-package-detail-view.component.html',
  styleUrls: ['./item-package-detail-view.component.css']
})
export class ItemPackageDetailViewComponent implements OnInit {

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
    this.itemPackage = this.getNewPackage();
    this.itemPackageReview = this.getNewItemPackageReview();
    this.itemService.itemPackageSub.observers = [];
    this.itemService.itemPackageSub.subscribe((itemPackageObj) => {
      // console.log(itemPackageObj)
      this.itemPackageObj = itemPackageObj;
      this.getPackageItemSelected(itemPackageObj.itemPackage);
    })
  }

  ngOnInit(): void {
  }

  addToCart() {
    this.itemPackage.orderDetail.orderDetailType = 'ItemPackage';
    this.shopCartService.shopCartSub.next(this.itemPackage);
  }

  getPackageItemSelected(itemPackage) {
    console.log(itemPackage)
    if (document.getElementById('package-back-btn') !== null) {
      document.getElementById('package-back-btn').click()
    }
    this.itemService.getItemPackageSelected(itemPackage.itemPackageId, itemPackage.itemPackageType).subscribe((itemPackageObj) => {
      if (itemPackage.itemPackageType === 'Item') {
        this.itemPackage = itemPackageObj.item.itemPackage;
      } else if (itemPackage.itemPackageType === 'Package') {
        this.itemPackage = itemPackageObj.packageItem.itemPackage;
        this.itemPackage.packageItemItems = itemPackageObj.packageItem.packageItemItems;
      }
      console.log(this.itemPackage)
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
    this.itemPackageReview.reviewType = 'ItemPackage';
    this.itemPackageReview.customerProfile.customerProId = JSON.parse(localStorage.getItem('user')).customerProfile.customerProId;
    this.itemService.addItemReview(this.itemPackageReview).subscribe((itemPackageReview) => {
      this.itemPackageReviews.push(itemPackageReview);
      this.reviewForm.resetForm()
      this.itemPackageReview = this.getNewItemPackageReview();
    })
  }

  addItemReviewResponse(itemPackageReview, response) {
    let itemReviewResponseId = '';
    if (itemPackageReview.responseByMe !== undefined) {
      itemReviewResponseId = itemPackageReview.responseByMe.itemReviewResponseId
    }
    let responseTemp = response;
    if (itemPackageReview.responseByMe !== undefined && itemPackageReview.responseByMe.response === response) {
      response = 'remove';
    }
    let itemReviewResponse = {
      itemReviewResponseId: itemReviewResponseId,
      response: response,
      itemReview: {itemReviewId: itemPackageReview.itemReviewId},
      customerProfile: {customerProId: JSON.parse(localStorage.getItem('user')).customerProfile.customerProId}
    };
    // console.log(itemReviewResponse)
    this.itemService.addItemResponse(itemReviewResponse).subscribe((itemReviewResponseObj) => {
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

  getItemPackageReviews() {
    this.review = true;
    this.itemService.getItemReviews(this.itemPackage.itemPackageId, JSON.parse(localStorage.getItem('user')).userId, 'ItemPackage').subscribe((itemPackageReviews) => {
      this.itemPackageReviews = itemPackageReviews;
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
