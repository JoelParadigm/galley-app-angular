import {TagModel} from "./tag.model";
import {SafeUrl} from "@angular/platform-browser";

export interface ImageDisplayModel {
  id: number;
  name: string;
  imageData: string;
  hashtags: TagModel[];
}
