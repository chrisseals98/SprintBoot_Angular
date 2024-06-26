import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  template: `
  <div style="text-align:center"class="container">
    <h1>
      Welcome {{title}}!
    </h1>
    <div class="container">
      <p>Message from server: <span>{{data.content}}</span></p>
    </div>
  </div>
  `,
  styles: []
})
export class AppComponent {
  title = 'client';
  data = {} as any;
  constructor (private http: HttpClient) {
    this.http.get("resource").subscribe(data => this.data = data);
  }
}
