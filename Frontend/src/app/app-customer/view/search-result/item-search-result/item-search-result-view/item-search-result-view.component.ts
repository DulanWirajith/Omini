import { Component, OnInit } from '@angular/core';
import {ItemService} from "../../../../_service/item.service";
import {DomSanitizer} from "@angular/platform-browser";
import {Lightbox} from "ngx-lightbox";
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-item-search-result-view',
  templateUrl: './item-search-result-view.component.html',
  styleUrls: ['./item-search-result-view.component.css']
})
export class ItemSearchResultViewComponent implements OnInit {

  item;
  _album: [];
  // lightbox;
  firstImg = 0;
  secondImg = 1;
  thirdImg = 2;

  getIndex(value) {
    return value;
  }

  constructor(private itemService: ItemService, private sanitizer: DomSanitizer, private lightbox: Lightbox) {
    this.item = this.itemService.getNewItem();
    itemService.itemSub.subscribe((item) => {
      this.item = item;
      this.getAlbum(this.item);
    })
  }

  ngOnInit(): void {
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
    imageList.itemImgs.forEach(element => {
      promises.push(this.setAlbum(element));
    });
    Promise.all(promises).then((result: []) => {
      this._album = result;
      console.log(this._album);
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
    let albumImgSrc;
    albumImgSrc = this._album[imgNo]['thumb'];
    return albumImgSrc;
  }

}
