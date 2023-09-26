import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-driver-registration',
  templateUrl: './driver-registration.component.html',
  styleUrls: ['./driver-registration.component.css'],
})
export class DriverRegistrationComponent {
  driverRegistrationUrl: string =
    'http://localhost:8080/api/v1/drivers/register';

  driver = {
    name: '',
    email: '',
    password: '',
    phone: '',
    registration: '',
    carType: 'sedan', // Default car type
  };

  constructor(private httpClient: HttpClient, private router: Router) {}

  onSubmit() {
    console.log(this.driver);
    this.httpClient
      .post(this.driverRegistrationUrl, this.driver)
      .subscribe((newDriver: any) => {
        console.log(newDriver);
        this.router.navigate(['/DriverDashboard', newDriver['id']]);
      });
  }
}
