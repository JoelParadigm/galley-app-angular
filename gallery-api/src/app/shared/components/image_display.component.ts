import { Injectable } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class ImageUtils {

  constructor(private sanitizer: DomSanitizer) {}
  public blobToSafeUrl(imageBlob: Blob): SafeUrl {
    const reader = new FileReader();
    reader.readAsDataURL(imageBlob);
    return new Promise<SafeUrl>((resolve, reject) => {
      reader.onloadend = () => {
        const base64data = reader.result as string;
        const safeImageUrl = this.sanitizer.bypassSecurityTrustUrl(base64data);
        resolve(safeImageUrl);
      };
      reader.onerror = () => {
        reject('Failed to read the image data.');
      };
    });
  }
}
