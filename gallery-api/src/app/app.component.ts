import { Component, inject } from '@angular/core';
import {GalleryService} from "./core/services/gallery.service";
import {ImageDisplayModel} from "./core/models/image_thumbnail.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor() {
  }

  username = 'Joel';
}
