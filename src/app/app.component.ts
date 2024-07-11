import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav class="navbar">
    <div class="together">
      <a class="navbar-brand" [routerLink]="['/']"><button type="button" class="btn btn-light">REVA</button></a>
      <a class="navbar-item nav-link" [routerLink]="['/application']"><button type="button" class="btn btn-light">New Application</button></a>
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
  
  constructor(private http: HttpClient) {}
  
  logout() {
    this.http.post("/logout", {}, { responseType: "text" }).subscribe(_ => location.reload());
  }
}
