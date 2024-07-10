import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-app',
  template: `
  <div class="container">
    <form class="col-md-6" id="form" [formGroup]="form">
      <div>
        <label class="form-label">Application Type</label>
        <select id="applicationType" name="applicationType" class="form-select" formControlName="applicationType">
          <option *ngFor='let type of appTypes'>{{type}}</option>
        </select>
      </div>
      <div>
        <label for="address" class="form-label">Address</label>
        <input id="address" name="address" type="text" class="form-control" formControlName="address">
      </div>
      <div>
        <label for="city" class="form-label">City</label>
        <input id="city" name="city" type="text" class="form-control" formControlName="city">
      </div>
      <div>
        <label for="state" class="form-label">State</label>
        <input id="state" name="state" type="text" class="form-control" formControlName="state">
      </div>
      <div>
        <label for="latitude" class="form-label">Latitude</label>
        <input id="latitude" name="latitude" type="number" class="form-control" formControlName="latitude">
      </div>
      <div>
        <label for="longitude" class="form-label">Longitude</label>
        <input id="longitude" name="longitude" type="number" class="form-control" formControlName="longitude">
      </div>
      <button type="button" class="btn btn-light mt-3" (click)="onSubmit()">Submit</button>
    </form>
  </div>
  `,
  styles: [
  ]
})
export class CreateAppComponent {
  appTypes = [
    "Myself", "For_Another", "Company", "Public_Agency", "Jointly_Owned"
  ];

  form = new FormGroup({
    applicationType: new FormControl("Myself"),
    status: new FormControl("In_Progress"),
    address: new FormControl(""),
    city: new FormControl(""),
    state: new FormControl(""),
    latitude: new FormControl(""),
    longitude: new FormControl("")
  });

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
      this.http.post("application", this.form.value).subscribe(_ => this.router.navigateByUrl(""));
  }
}
