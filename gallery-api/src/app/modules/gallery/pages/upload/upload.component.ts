import {Component, inject, OnInit} from '@angular/core';
import {TagModel} from "../../../../core/models/tag.model";
import {ImageModel} from "../../../../core/models/image.model";
import {SafeUrl} from "@angular/platform-browser";
import {GalleryService} from "../../../../core/services/gallery.service";
import {ImageUtils} from "../../../../shared/components/image_display.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.css'
})
export class UploadComponent implements OnInit {
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);

  description: string | null = '';
  title: string = 'Test';
  tagsString: string = '';
  tags: TagModel[] = [];
  public file : File | undefined;
  imageUrl: string | ArrayBuffer | null = null;
  imageTest: ImageModel | undefined;

  constructor(private route: ActivatedRoute, private router: Router) {
    console.log("view page model loaded");

  }

  ngOnInit(): void {
    this.galleryService
      .getImage(6)
      .subscribe(image => {
          this.imageTest = image;
          console.log(image);
        }
      );
  }
  processFile(imageInput: HTMLInputElement) {
    console.log('response got'+imageInput);
    if (imageInput.files && imageInput.files.length > 0) {
      console.log('file [0] file'+ imageInput.files[0]);
      this.file = imageInput.files[0];
      console.log('response got'+this.file);
      this.readFile(this.file);
    }
  }
  private readFile(file: File): void {
    const reader = new FileReader();

    reader.onload = (e) => {
      // Read file content as a data URL (base64 encoded string)
      // @ts-ignore
      this.imageUrl = e.target?.result;
      // console.log('url'+this.imageUrl);
    };

    // Start reading the file
    reader.readAsDataURL(file);
  }

  CommitTags() {
    this.parseTags();
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

  Upload() {
    let image;
    if (this.title && this.imageUrl && this.imageTest) {
      image = {
          id: null,
          name: this.imageTest?.name,
          description: this.imageTest?.description,
          uploadDate: this.imageTest?.uploadDate,
          imageData: this.imageTest?.imageData,
          imageThumbnail: this.imageTest?.imageThumbnail,
          hashtags: this.imageTest?.hashtags
      }

      console.log('image:'+image);
      console.log('imageTest:'+this.imageTest);
      // @ts-ignore
      this.galleryService.addImage(image).subscribe(
        (response) => {
          console.log('Image added successfully:', response);
          // Handle success response here
          // @ts-ignore
          this.router.navigate(['..'], { relativeTo: this.route });
        },
        (error) => {
          console.error('Error adding image:', error);
          // Handle error here
        }
      );
      console.log("Added image");


    }
  }

  Cancel() {
    this.router.navigate(['..'], { relativeTo: this.route });
  }

  extarctBase64(imageUrl: any): string{
    const prefix = "data:image/jpeg;base64,";
    if (imageUrl.startsWith(prefix)) {
      // Extract the base64 data portion
      const base64Data = imageUrl.slice(prefix.length);

      // Now base64Data contains only the base64-encoded image data
      return base64Data;
    } else {
      return ' ';
    }
  }
}
