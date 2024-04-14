import {TagModel} from "./tag.model";

export interface ImageModel {
  id: number;
  name: string;
  description: string;
  uploadDate: Date;
  imageData: string;
  hashtags: TagModel[];
}
