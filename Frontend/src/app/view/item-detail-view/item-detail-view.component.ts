import {Component, OnInit, ViewChild} from '@angular/core';
import {environment} from "../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {ItemGService} from "../../_service/item-g.service";
import {ShopCartService} from "../../app-customer/_service/shop-cart.service";
import * as $ from "jquery";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-item-detail-view',
  templateUrl: './item-detail-view.component.html',
  styleUrls: ['./item-detail-view.component.css']
})
export class ItemDetailViewComponent implements OnInit {

  itemObj = {
    item: undefined,
    backBtn: undefined,
    cart: undefined
  };
  item;
  _album: [];
  review = false;
  itemReview;
  itemReviews = [];
  @ViewChild('reviewForm', {static: true}) public reviewForm: NgForm;

  constructor(private itemService: ItemGService, private sanitizer: DomSanitizer, private lightbox: Lightbox, private shopCartService: ShopCartService) {
    this.item = this.getNewItem();
    this.itemReview = this.getNewItemReview();
    // this.itemService.itemSub.observers = [];
    // this.itemService.itemSub.subscribe((itemObj) => {
    //   this.itemObj = itemObj;
    //   this.getItemSelected(itemObj.item);
    //   // console.log(itemObj)
    // })
  }

  ngOnInit(): void {
  }

  getItemSelected(item) {
    if (document.getElementById('item-back-btn') !== null) {
      document.getElementById('item-back-btn').click()
    }
    this.itemService.getItemPackageSelected(item.itemId, 'Item').subscribe((itemObj) => {
      this.item = itemObj;
      if (item.orderDetail !== undefined) {
        this.item.orderDetail.quantity = item.orderDetail.quantity
      }
      this.getAlbum(this.item);
    })
  }

  addItemReview() {
    this.itemReview.item = {
      itemId: this.item.itemId
    };
    this.itemReview.reviewType = 'Item';
    this.itemReview.customerProfile.customerProId = JSON.parse(localStorage.getItem('user')).customerProfile.customerProId;
    this.itemService.addItemPackageReview(this.itemReview).subscribe((itemReview) => {
      this.itemReviews.push(itemReview);
      this.reviewForm.resetForm()
      this.itemReview = this.getNewItemReview();
    })
  }

  addItemReviewResponse(itemReview, response) {
    let itemReviewResponseId = '';
    if (itemReview.responseByMe !== undefined) {
      itemReviewResponseId = itemReview.responseByMe.itemReviewResponseId
    }
    let responseTemp = response;
    if (itemReview.responseByMe !== undefined && itemReview.responseByMe.response === response) {
      response = 'remove';
    }
    let itemReviewResponse = {
      itemReviewResponseId: itemReviewResponseId,
      response: response,
      itemReview: {itemReviewId: itemReview.itemReviewId},
      customerProfile: {customerProId: JSON.parse(localStorage.getItem('user')).customerProfile.customerProId}
    };
    // console.log(itemReviewResponse)
    this.itemService.addItemPackageResponse(itemReviewResponse).subscribe((itemReviewResponseObj) => {
      if (itemReviewResponseObj.response === 'like') {
        if (itemReview.responseByMe !== undefined) {
          itemReview.dislikeCount--;
        }
        itemReview.likeCount++;
        itemReview.responseByMe = itemReviewResponseObj;
      } else if (itemReviewResponseObj.response === 'dislike') {
        itemReview.dislikeCount++;
        if (itemReview.responseByMe !== undefined) {
          itemReview.likeCount--;
        }
        itemReview.responseByMe = itemReviewResponseObj;
      } else if (itemReviewResponseObj.response === 'remove') {
        if (responseTemp === 'like') {
          // if (itemReview.likeCount > 0) {
          itemReview.likeCount--;
          // }
        } else if (responseTemp === 'dislike') {
          // if (itemReview.dislikeCount > 0) {
          itemReview.dislikeCount--;
          // }
        }
        itemReview.responseByMe = undefined;
      }
    })
  }

  getItemReviews() {
    this.review = true;
    this.itemService.getItemPackageReviews(this.item.itemId, JSON.parse(localStorage.getItem('user')).userId, 'Item').subscribe((itemReviews) => {
      this.itemReviews = itemReviews;
      // console.log(this.itemReviews)
    })
  }

  addToCart() {
    this.item.orderDetail.orderDetailType = 'Item';
    this.shopCartService.shopCartSub.next(this.item);
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
        src: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemImgName),
        caption: img.itemImgId,
        thumb: this.sanitizer.bypassSecurityTrustUrl(environment.image_url + img.itemImgName)
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

  getImageSrc(itemPackageImage) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemPackageImage.itemPackage.itemImageName);
  }

  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getNewItem() {
    return {
      itemId: "",
      name: "",
      quantity: 1,
      makeToOrder: false,
      itemCount: 0,
      discount: "",
      discountType: "None",
      price: "",
      description: "",
      itemPackageImages: [],
      itemImgsRaw: [],
      available: false,
      businessProfileCategory: {
        businessProfile: undefined,
        businessCategory: undefined
      },
      itemPackageItemPackageFeatures: [],
      isNewItem: false,
      isUpdateItem: false,
    }
  }

  getNewItemReview() {
    return {
      itemReviewId: '',
      description: '',
      rating: '',
      likeCount: 0,
      dislikeCount: 0,
      postedDate: '',
      item: undefined,
      customerProfile: {
        customerProId: ''
      }
    }
  }
}
