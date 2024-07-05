import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { AppsComponent } from './apps/apps.component';
import { HomeComponent } from './home/home.component';
import { DocumentsComponent } from './documents/documents.component';
import { CreateAppComponent } from './create-app/create-app.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: "documents", component: DocumentsComponent },
  { path: "application", component: CreateAppComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    AppsComponent,
    HomeComponent,
    DocumentsComponent,
    CreateAppComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
