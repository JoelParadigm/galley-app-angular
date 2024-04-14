import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {GalleryService} from "../../../../core/services/gallery.service";
import {ImageModel} from "../../../../core/models/image.model";
import {ImageUtils} from "../../../../shared/components/image_display.component";
import { SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-view',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './view.component.html',
  styleUrl: './view.component.css'
})
export class ViewPageComponent implements OnInit {
  image: ImageModel | undefined;
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);
  imageUrl: SafeUrl | undefined;
  date: string | undefined;

  constructor(private route: ActivatedRoute) {
    console.log("view page model loaded");
  }

  ngOnInit(): void {
    this.route.params
      .subscribe(params => {
        const id = params['id'];
        console.log(id); // Use the id or assign it to a local variable
      }
    );
    this.galleryService
      .getImage(this.route.snapshot.params['id'])
      .subscribe(image => {
        this.image = image;
        // @ts-ignore
        this.imageUrl = this.imageUtils.base64ToSafeUrl(this.image.imageData, 'image/jpeg');
        this.date = this.image.uploadDate.toString().split('T')[0];
      }
    );

  }
}
