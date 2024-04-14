import { Injectable } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {ImageDisplayModel} from "../../core/models/image_thumbnail.model";

@Injectable({
  providedIn: 'root'
})
export class ImageUtils {

  constructor(private sanitizer: DomSanitizer) {}

  base64ToSafeUrl(base64Data: string, mimeType: string): SafeUrl {
    const imageUrl = `data:${mimeType};base64,${base64Data}`;
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }
}
