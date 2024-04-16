import {Component, inject, OnInit} from '@angular/core';
import {ImageModel} from "../../../../core/models/image.model";
import {GalleryService} from "../../../../core/services/gallery.service";
import {ImageUtils} from "../../../../shared/components/image_display.component";
import {SafeUrl} from "@angular/platform-browser";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {TagModel} from "../../../../core/models/tag.model";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent implements OnInit {
  image: ImageModel | undefined;
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);
  imageUrl: SafeUrl | undefined;
  description: string | null = '';
  title: string = '';
  tagsString: string = '';
  tags: TagModel[] = [];

  constructor(private route: ActivatedRoute, private router: Router) {
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
          this.description = this.image.description;
          this.title = this.image.name;
          this.tags = this.image.hashtags;
          this.initTagString();
        }
      );

  }
  initTagString(): void{
    for (let i = 0; i < this.tags.length; i++) {
      this.tagsString += this.tags[i].name;
      if (i < this.tags.length - 1) {
        this.tagsString += ', ';
      }
    }
  }

  parseTags(): void {
    this.tags = [];
    this.tagsString
      .split(/[,\s]+/)
      .forEach(tag => {
        // Check if the tag contains only alphanumeric characters
        if (!/^[a-zA-Z0-9]+$/.test(tag))
          return;
        if (tag.length < 2 || tag.length > 30)
          return;
        // Check if the tag is not already in the tags array
        if (this.tags.some(t => t.name === tag.trim()))
          return;
        this.tags.push({ id: 0, name: tag.trim() });
      });
  }
  CommitTags() {
    this.parseTags();
  }

  SaveChanges() {
    if (this.image) {
      this.image.description = this.description;
      this.image.name = this.title;
      this.image.hashtags = this.tags;
      this.galleryService.addImage(this.image).subscribe(
        (response) => {
          console.log('Image added successfully:', response);
          // Handle success response here
          // @ts-ignore
          this.router.navigate(['../../view/', this.image.id], { relativeTo: this.route });
        },
        (error) => {
          console.error('Error adding image:', error);
          // Handle error here
        }
      );
      console.log("Added image");


    }
  }

  DeleteImage() {
    if (this.image) {
      this.galleryService.deleteImage(this.image.id).subscribe(data => {
        // @ts-ignore
        this.router.navigate(['../..'], { relativeTo: this.route });
      })
    }
  }
}
