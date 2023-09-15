import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-registration',
  templateUrl: './customer-registration.component.html',
  styleUrls: ['./customer-registration.component.css']
})
export class CustomerRegistrationComponent {
  customerRegistrationUrl: string = 'http://localhost:8080/api/v1/customers/register'

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {}

  onCustomerRegistration(customer: {
    name: string,
    email: string,
    password: string,
    confirmPassword: string
  }) {
    this.httpClient.post(this.customerRegistrationUrl, customer)
    .subscribe((newCustomer: any) => {
      console.log(newCustomer)
      this.router.navigate(['/CustomerDashboard', newCustomer['id']]);
    })
  }

}
