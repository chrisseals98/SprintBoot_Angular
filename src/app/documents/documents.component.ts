import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-documents',
  template: `
    <table class="table table-striped">
      <thead>
        <tr>
          <th>id</th>
          <th>File Type</th>
          <th>File Name</th>
          <th>Uploader</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let document of documents">
          <td>{{document.id}}</td>
          <td>{{document.fileType}}</td>
          <td>{{document.fileName}}</td>
          <td>{{document.uploader.firstName}}, {{document.uploader.lastName}}</td>
        </tr>
        <tr *ngIf="documents.length == 0">
          No documents have been uploaded for this application
        </tr>
      </tbody>
    </table>
    <button type="button" class="btn btn-light" [routerLink]="['']">Go back</button>
  `,
  styles: [
  ]
})
export class DocumentsComponent implements OnInit {
  applicationId = "";
  documents: any[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {}
  
  ngOnInit(): void {
    let param = this.route.snapshot.paramMap.get("id");
    this.applicationId = param ? param : "";

    this.http.get<any[]>("documents/application", {
      params: { id: this.applicationId }
    }).subscribe(response => {
      this.documents = response
    });
  }
}
