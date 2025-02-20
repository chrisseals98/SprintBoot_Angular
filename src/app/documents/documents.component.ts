import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';
import { LoadingService } from '../loading.service';

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
          <th>Status</th>
          <th>Download</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let document of documents">
          <td>{{document.id}}</td>
          <td>{{document.fileType}}</td>
          <td>{{document.fileName}}</td>
          <td>{{document.uploader.firstName}}, {{document.uploader.lastName}}</td>
          <td>{{document.status}}</td>
          <td><button class="btn btn-light" type="button" (click)="download(document.fileName)">Download</button></td>
          <td *ngIf="document.status != 'completed'"><a [href]="'/document/sign?id=' + applicationId" class="restrict-width"><button type="button" class="btn btn-primary">Sign</button></a></td>
          <td *ngIf="document.status == 'completed'"><button type="button" class="btn btn-danger" (click)="delete(document.id)">Delete</button>
          </td>
        </tr>
        <tr *ngIf="documents.length == 0">
          <div>No documents have been uploaded for this application, please click <a [href]="'/document/sign?id=' + applicationId" class="restrict-width">here</a> to sign.</div>
        </tr>
      </tbody>
    </table>
    <div class="row">
      <button type="button" class="restrict-width btn btn-light" [routerLink]="['']">Go back</button>
    </div>
  `,
  styles: [
  ]
})
export class DocumentsComponent implements OnInit {
  applicationId = "";
  documents: any[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute, private loadingService: LoadingService) {}
  
  ngOnInit(): void {
    this.loadingService.showLoading();
    let param = this.route.snapshot.paramMap.get("id");
    this.applicationId = param ? param : "";
    this.getDocuments();
  }

  getDocuments() { 
    this.http.get<any[]>("document/application", {
      params: { id: this.applicationId }
    }).subscribe(response => {
      this.documents = response;
      this.loadingService.hideLoading();
    });
  }

  download(envelopeId: string) {
    this.http.get<any>("document/attachments", {
      params: { id: envelopeId },
      responseType: 'blob' as 'json'
    }).subscribe((response) => {
      saveAs(response, "test.pdf")
    })
  }

  sign() {
    this.http.get("document/sign", { params: { id: this.applicationId }, observe: 'response', responseType: 'text' }).subscribe(response => console.log(response));
  }

  delete(documentId: number) {
    this.loadingService.showLoading();
    this.http.delete("document", { params: { id: documentId } }).subscribe(response => {
      this.getDocuments();
      this.loadingService.hideLoading();
    })
  }
}
