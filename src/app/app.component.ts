import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav class="navbar">
    <div class="together">
      <a class="navbar-brand" [routerLink]="['/']">REVA</a>
      <a class="navbar-item nav-link" [routerLink]="['/application']">New Application</a>
    </div>
    <a class="navbar-item nav-link" href="/logout">Logout</a>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: [
    ".together { display: flex; align-items: center; justify-content: normal }"
  ]
})
export class AppComponent {}