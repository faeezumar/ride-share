import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.css']
})
export class CustomerDashboardComponent implements OnInit {
  url = 'http://localhost:8080/api/v1/customers/one/'
  customerId: any
  customer: any

  constructor(
    private httpClient: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.customerId = params.get('id');
      this.fetchCustomer(this.customerId);
    });
  }

  fetchCustomer(id: any) {
    this.httpClient.get(this.url + id).subscribe((response) => {
      this.customer = response
    })
  }

  onRideRequestForm() {
    this.router.navigate(['/RideRequest', this.customerId]);
  }

}
