import {Component, inject} from '@angular/core';
import {TagModel} from "../../../../core/models/tag.model";
import {ImageModel} from "../../../../core/models/image.model";
import {SafeUrl} from "@angular/platform-browser";
import {GalleryService} from "../../../../core/services/gallery.service";
import {ImageUtils} from "../../../../shared/components/image_display.component";

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [],
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.css'
})
export class UploadComponent {
  image:ImageModel={
    id:null,
    name:'',
    description:'',
    uploadDate:null,
    imageData:'',
    hashtags:[]
  };
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);
  imageUrl: SafeUrl | undefined;
  description: string | null = '';
  title: string = '';
  tagsString: string = '';
  tags: TagModel[] = [];
  public file : File | undefined;
  url: string = "";

  processFile(imageInput: HTMLInputElement) {
    console.log('response got'+imageInput);
    if (imageInput.files && imageInput.files.length > 0) {
      const file = imageInput.files[0];
      console.log('response got'+file);
      this.file = file

    }
  }

  fileToString(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      // Setup onload event callback
      reader.onload = () => {
        const fileContent = reader.result as string; // This is the file content as a string
        resolve(fileContent);
      };

      // Setup onerror event callback
      reader.onerror = error => {
        reject(error);
      };

      // Read the file as text
      reader.readAsText(file);
    });
  }
}
