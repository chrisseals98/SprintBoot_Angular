import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { LoadingService } from './loading.service';

@Component({
  selector: 'app-root',
  template: `
  <nav class="navbar">
    <div class="together">
      <a class="navbar-brand" [routerLink]="['/']"><button type="button" class="btn btn-light">REVA</button></a>
      <a class="navbar-item nav-link" [routerLink]="['/application']"><button type="button" class="btn btn-light">New Application</button></a>
      <span class="navbar-item" *ngIf="isLoading">
        <span class="spinner-border spinner-border-sm" role="status"></span>
        Loading...
      </span>
    </div>
    <a class="navbar-item nav-link" (click)="logout()"><button type="button" class="btn btn-light">Logout</button></a>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: [
    ".together { display: flex; align-items: center; justify-content: normal }"
  ]
})
export class AppComponent {
  
  constructor(private http: HttpClient, private loadingService: LoadingService) {}
  
  get isLoading(): boolean {
    return this.loadingService.isLoading;
  }

  logout() {
    this.http.post("/logout", {}, { responseType: "text" }).subscribe(_ => location.reload());
  }
}
