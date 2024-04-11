import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {GalleryRoutingModule} from "./gallery-routing.module";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    GalleryRoutingModule,
    FormsModule
  ]
})
export class GalleryModule { }
