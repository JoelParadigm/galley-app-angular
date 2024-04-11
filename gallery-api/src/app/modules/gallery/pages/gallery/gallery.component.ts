import { Component, inject, OnInit } from "@angular/core";
import { ImageDisplayModel } from "../../../../core/models/image_thumbnail.model";
import { GalleryService } from "../../../../core/services/gallery.service";
import { PageModel } from "../../../../core/models/page.model";
import { ImageUtils } from "../../../../shared/components/image_display.component";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-explore-page',
  templateUrl: './gallery.component.html',
  styleUrl: './gallery.component.css'
})
export class GalleryPageComponent implements OnInit {
  images: ImageDisplayModel[] | undefined;
  galleryService = inject(GalleryService);
  imageUtils = inject(ImageUtils);

  // <search criteria fields> //
  public filterByDescription = '';
  public filterByTags = '';
  public filterByName = '';

  // <paging variables> //
  public page: number = 0;
  public pageSize: number = 12;
  public lastPage: number = 0;
  public canGoToPreviousPage: boolean = false;
  public canGoToNextPage: boolean = false;

  ngOnInit() {
    this.fetchPage(this.page);
  }

  fetchPage(page: number): void {
    if (this.filterByDescription || this.filterByTags || this.filterByName) {
      this.galleryService.searchImages(this.filterByDescription, this.filterByTags, this.filterByName, page, this.pageSize)
        .subscribe((response: PageModel) => {
          this.images = response.content
          this.lastPage = response.totalPages - 1;
        });
    } else {
      this.galleryService.getAllImages(page, this.pageSize)
        .subscribe((response: PageModel) => {
          this.images = response.content
          this.lastPage = response.totalPages - 1;

        });
    }
    // update page navigation variables
    this.canGoToPreviousPage = this.page > 0;
    this.canGoToNextPage = this.page < this.lastPage;
  }

  doSearch(): void {
    this.fetchPage(0);
  }

  isCollapsed = true;
  toggleCollapsible() {
    this.isCollapsed = !this.isCollapsed; // Toggle collapsible state
  }

  goToPreviousPage(): void {
    if (this.canGoToPreviousPage) {
      this.fetchPage(this.page - 1);
    }
  }
  goToNextPage(): void {
    if (this.canGoToNextPage) {
      this.fetchPage(this.page + 1);
    }
  }
}
