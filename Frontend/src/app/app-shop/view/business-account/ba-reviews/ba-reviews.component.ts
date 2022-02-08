import {Component, OnInit} from '@angular/core';
import {BusinessAccountService} from "../../../_service/business-account.service";

@Component({
  selector: 'app-ba-reviews',
  templateUrl: './ba-reviews.component.html',
  styleUrls: ['./ba-reviews.component.css']
})
export class BaReviewsComponent implements OnInit {

  businessReviews = [];

  constructor(private businessAccService: BusinessAccountService) {
  }

  ngOnInit(): void {
    this.getBusinessReviews();
  }

  getBusinessReviews() {
    this.businessAccService.getBusinessReviews(JSON.parse(localStorage.getItem('user')).userId, '0').subscribe((businessReviews) => {
      this.businessReviews = businessReviews;
    })
  }
}
