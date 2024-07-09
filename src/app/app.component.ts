import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
  <nav class="navbar">
    <a class="navbar-brand" [routerLink]="['/']">REVA</a>
    <a class="navbar-item nav-link" [routerLink]="['/application']">New Application</a>
  </nav>
  <router-outlet></router-outlet>
  `,
  styles: [
    "nav { justify-content: normal }"
  ]
})
export class AppComponent {}