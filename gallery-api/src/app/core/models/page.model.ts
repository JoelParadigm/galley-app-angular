import {ImageDisplayModel} from "./image_thumbnail.model";
export interface PageModel {
  content: ImageDisplayModel[];
  "pageable": {
    "sort": {
      "sorted": boolean,
      "unsorted": boolean,
      "empty": boolean
    },
    "offset": number,
    "pageNumber": number,
    "pageSize": number,
    "paged": boolean,
    "unpaged": boolean
  },
  "totalElements": number,
  "totalPages": number,
  "last": boolean,
  "size": number,
  "number": number,
  "sort": {
    "sorted": boolean,
    "unsorted": boolean,
    "empty": boolean
  },
  "numberOfElements": number,
  "first": boolean,
  "empty": boolean
}
