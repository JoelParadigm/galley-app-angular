import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { MenuModule } from './core/core.module';
import { RouterOutlet } from '@angular/router';
import { AppRoutingModule } from './app.routes';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [AppComponent],
    imports: [
        BrowserModule,
        MenuModule,
        RouterOutlet,
        AppRoutingModule,
        HttpClientModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
