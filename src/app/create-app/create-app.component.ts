import { Component } from '@angular/core';

@Component({
  selector: 'app-create-app',
  template: `
  <div class="col-12">
    <form class="col-md-6" id="form">
      <div>
        <label class="form-label">Application Type</label>
        <select id="application-type" name="application-type" class="form-select">
          <option *ngFor='let type of appTypes'>{{type}}</option>
        </select>
      </div>
      <div>
        <label for="address" class="form-label">Address</label>
        <input id="address" name="address" type="text" class="form-control">
      </div>
      <div>
        <label for="city" class="form-label">City</label>
        <input id="city" name="city" type="text" class="form-control">
      </div>
      <div>
        <label for="state" class="form-label">State</label>
        <input id="state" name="state" type="text" class="form-control">
      </div>
      <div>
        <label for="lat" class="form-label">Latitude</label>
        <input id="lat" name="lat" type="number" class="form-control">
      </div>
      <div>
        <label for="long" class="form-label">Longitude</label>
        <input id="long" name="long" type="number" class="form-control">
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
    "Myself", "For Another", "Company", "Public Agency", "Jointly Owned"
  ];

  onSubmit() {
      //need to send form here
  }
}
