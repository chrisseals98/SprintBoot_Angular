import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-apps',
  template: `
    <table class="table table-striped">  
      <thead>  
          <tr>
            <th>Id</th>  
            <th>Application Type</th>
            <th>Status</th>  
            <th>Requestor</th>  
            <th>Coordinates</th>
            <th>Address</th>
            <th>Documents</th>
          </tr>  
      </thead>  
      <tbody>  
        <tr *ngFor="let app of apps"> 
          <td>{{ app.id }}</td>  
          <td>{{ app.applicationType }}</td>
          <td>{{ app.status }}</td>  
          <td>{{ app.requestor.firstName }}, {{ app.requestor.lastName }}</td>  
          <td>{{ app.latitude }}, {{ app.longitude }}</td>  
          <td>{{ app.address }}, {{ app.city }}, {{ app.state }}</td>
          <td>
            <button type="button" class="btn btn-secondary" [routerLink]="['/documents', {id: app.id}]">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-info-circle" viewBox="0 0 16 16">
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0"/>
              </svg>
            </button>
          </td> 
        </tr> 
      </tbody>  
    </table>
  `,
  styles: [
  ]
})
export class AppsComponent {
  apps: any[] = [];

  constructor(private http: HttpClient) {
    this.http.get<any[]>("applications").subscribe(response => {
      this.apps = response;
    })
  }
}
