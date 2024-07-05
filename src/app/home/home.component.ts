import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  template: `
  <div style="text-align:center"class="container">
    <h1>
      REVA
    </h1>
    <app-apps></app-apps>
  </div>
  `,
  styles: [
  ]
})
export class HomeComponent {
  title = 'client';
  data = {} as any;
  constructor (private http: HttpClient) {
    this.http.get("resource").subscribe(data => this.data = data);
  }
}
