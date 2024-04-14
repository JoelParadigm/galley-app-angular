import {Component, inject, OnInit} from '@angular/core';
import {ImageModel} from "../../../../core/models/image.model";
import {GalleryService} from "../../../../core/services/gallery.service";
import {ImageUtils} from "../../../../shared/components/image_display.component";
import {SafeUrl} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent implements OnInit {
  image: ImageModel | undefined;
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);
  imageUrl: SafeUrl | undefined;

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
        }
      );

  }
}
