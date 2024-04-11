import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GalleryPageComponent } from "./pages/gallery/gallery.component";

const routes: Routes = [
  {path: 'gallery', component: GalleryPageComponent},
  {path: '', redirectTo: '/gallery', pathMatch: 'full'},
  {path: '**', redirectTo: '/gallery'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GalleryRoutingModule { }
