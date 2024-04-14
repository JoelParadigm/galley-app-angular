import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'gallery', loadChildren: () => import('./modules/gallery/gallery.module').then(m => m.GalleryModule) },
  { path: '', redirectTo: 'gallery', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

