import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-ba-reviews',
  templateUrl: './ba-reviews.component.html',
  styleUrls: ['./ba-reviews.component.css']
})
export class BaReviewsComponent implements OnInit {

  businessReview = {
    rating1: 0,
    rating2: 0,
    businessReviews: []
  };

  constructor(private businessAccService: BusinessAccountService) {
  }

  ngOnInit(): void {
    this.getBusinessReviews();
  }

  getBusinessReviews() {
    this.businessAccService.getBusinessReviews(JSON.parse(localStorage.getItem('user')).userId, '0').subscribe((businessReview) => {
      this.businessReview = businessReview;
    })
  }
}
