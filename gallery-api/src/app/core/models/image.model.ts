import {TagModel} from "./tag.model";

export interface ImageModel {
  id: number | null;
  name: string;
  description: string | null;
  uploadDate: Date | null;
  imageData: string;
  hashtags: TagModel[];
}
