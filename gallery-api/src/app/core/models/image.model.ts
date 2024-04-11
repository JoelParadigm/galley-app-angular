import {TagModel} from "./tag.model";

export interface ImageModel {
  id: number;
  name: string;
  description: string;
  uploadDate: Date;
  imageData: Blob;
  imageThumbnail: Blob;
  hashtags: TagModel[];
}
