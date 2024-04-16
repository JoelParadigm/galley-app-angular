import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ImageDisplayModel } from "../models/image_thumbnail.model";
import { PageModel } from "../models/page.model";
import {ImageModel} from "../models/image.model";

@Injectable({
  providedIn: 'root'
})
export class GalleryService {
  private baseUrl = 'http://localhost:8080/gallery';
  constructor(private http: HttpClient) { }

  public getAllImages(page: number, size: number): Observable<PageModel> {
    return this.http.get<PageModel>(`${this.baseUrl}/images?page=${page}&size=${size}`);
  }

  public searchImages(description: string, tags: string, name: string, page: number, size: number): Observable<PageModel> {
    const params = new HttpParams()
    .set('description', description)
    .set('tags', tags)
    .set('name', name)
    .set('page', page.toString())
    .set('size', size.toString());
    console.log('Requesting URL:', `${this.baseUrl}/images/search?${params.toString()}`);
    return this.http.get<PageModel>(`${this.baseUrl}/images/search`, { params });
  }

  public getImage(id: number): Observable<ImageModel> {
    return this.http.get<ImageModel>(`${this.baseUrl}/images/view?imageId=${id}`);
  }
  public addImage(image: ImageModel | undefined): Observable<ImageModel> {
    console.log('addImage', image);
    console.log('Requesting URL:', `${this.baseUrl}/images/save`, image);
    return this.http.post<ImageModel>(`${this.baseUrl}/images/save`, image);
  }

  public deleteImage(id: number | null): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/images/delete?imageId=${id}`);
  }
}
