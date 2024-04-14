import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GalleryPageComponent} from "./pages/gallery/gallery.component";
import {ViewPageComponent} from "./pages/view/view.component";
import {EditComponent} from "./pages/edit/edit.component";
import {UploadComponent} from "./pages/upload/upload.component";


const routes: Routes = [
  { path: '', component: GalleryPageComponent},
  { path: 'view/:id', component: ViewPageComponent },
  { path: 'edit/:id', component: EditComponent },
  { path: 'upload', component: UploadComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GalleryRoutingModule { }
