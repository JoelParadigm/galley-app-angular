import { Component, inject, OnInit } from "@angular/core";
import { ImageDisplayModel } from "../../../../core/models/image_thumbnail.model";
import { GalleryService } from "../../../../core/services/gallery.service";
import { PageModel } from "../../../../core/models/page.model";
import { ImageUtils } from "../../../../shared/components/image_display.component";
import { FormsModule } from "@angular/forms";
import {NgOptimizedImage} from "@angular/common";
import {Router, RouterLink, UrlTree} from "@angular/router";

@Component({
  selector: 'app-explore-page',
  templateUrl: './gallery.component.html',
  styleUrl: './gallery.component.css',
  standalone: true,
  imports: [
    FormsModule,
    NgOptimizedImage,
    RouterLink,
  ],
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
          this.images = response.content;
          this.lastPage = response.totalPages - 1;
          this.updateNavigationVariables();
        });
    } else {
      this.galleryService.getAllImages(page, this.pageSize)
        .subscribe((response: PageModel) => {
          this.images = response.content;
          this.lastPage = response.totalPages - 1;
          this.updateNavigationVariables();
        });
    }

  }

  public updateNavigationVariables():void{
    // update page navigation variables
    this.canGoToPreviousPage = this.page > 0;
    this.canGoToNextPage = this.page < this.lastPage;
    console.log("page: "+this.page + " lastPage: "+this.lastPage)
    console.log("can go prev page: " +this.canGoToPreviousPage)
    console.log("can go next page: " +this.canGoToNextPage)
  }

  doSearch(): void {
    this.fetchPage(0);
    console.log("searching; description: "
      + this.filterByDescription
      + " name:"
      + this.filterByName
      + " tags: "
      + this.filterByTags)
  }

  isCollapsed = true;
  toggleCollapsible() {
    this.isCollapsed = !this.isCollapsed; // Toggle collapsible state
  }

  goToPreviousPage(): void {
    console.log("prev button pressed: " + this.canGoToPreviousPage);
    if (this.canGoToPreviousPage) {
      this.page -=1;
      console.log("loading page nr:" + this.page);
      this.fetchPage(this.page);
    }
  }
  goToNextPage(): void {
    console.log("next button pressed: " + this.canGoToNextPage);
    if (this.canGoToNextPage) {
      this.page+=1;
      console.log("loading page nr:" + this.page);
      this.fetchPage(this.page);
    }
  }
  constructor(private router: Router) {
    console.log("gallery page model loaded")
  }

  print(message:string){
    console.log(message);
  }
}
