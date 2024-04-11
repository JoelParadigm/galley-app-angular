import {TagModel} from "./tag.model";

export interface ImageDisplayModel {
  id: number;
  name: string;
  imageData: Blob;
  hashtags: TagModel[];
}
