import {Component, Input, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.css']
})
export class ImageSliderComponent implements OnInit {

  @Input() images;

  constructor(private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.showSlides();
  }

  showSlides() {
//     let slideIndex = 0;
//     let i;
//     let slides = document.getElementsByClassName("mySlides") as HTMLCollectionOf<HTMLElement>;
// console.log(slides)
//     if (slides !== undefined) {
//       // let dots = document.getElementsByClassName("dot");
//       for (i = 0; i < slides.length; i++) {
//         slides[i].style.display = "none";
//       }
//       slideIndex++;
//       if (slideIndex > slides.length) {
//         slideIndex = 1
//       }
//       // for (i = 0; i < dots.length; i++) {
//       //   dots[i].className = dots[i].className.replace(" active", "");
//       // }
//       slides[slideIndex - 1].style.display = "block";
//     }
//     // dots[slideIndex-1].className += " active";
//     setTimeout(this.showSlides, 2000); // Change image every 2 seconds
  }

  getImageSrc(itemImg) {
    // let imageData = 'data:' + itemImg.itemImgType + ';base64,' + itemImg.itemImg;
    // return this.sanitizer.bypassSecurityTrustUrl(imageData);
    return this.sanitizer.bypassSecurityTrustUrl(environment.image_url + itemImg.itemImgName);
  }
}
