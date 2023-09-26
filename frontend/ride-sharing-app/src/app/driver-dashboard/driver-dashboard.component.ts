import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-driver-dashboard',
  templateUrl: './driver-dashboard.component.html',
  styleUrls: ['./driver-dashboard.component.css'],
})
export class DriverDashboardComponent {
  isAvailable: boolean = false;
  interval: any;
  latitude: number = 0;
  longitude: number = 0;
  geoLocationUrl: string =
    'http://localhost:8080/api/v1/drivers/${driverId}/update-location';
  sendLocation: boolean = false;
  driverId: any = '';

  constructor(private httpClient: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.driverId = params.get('id');
    });
  }

  toggleAvailability() {
    this.sendLocation = !this.sendLocation;
    let options = {
      enableHighAccuracy: true,
      maximumAge: 150000,
      timeout: 30000,
    };
    if (navigator.geolocation && this.sendLocation) {
      this.interval = setInterval(() => {
        navigator.geolocation.getCurrentPosition((position) => {
          console.log('sending geo');
          this.postDriverLocation({
            latitude: position['coords']['latitude'],
            longitude: position['coords']['longitude'],
          });
        });
      }, 6000);
    } else {
      clearInterval(this.interval);
    }
  }

  postDriverLocation(position: any) {
    this.httpClient
      .post(
        'http://localhost:8080/api/v1/drivers/' +
          this.driverId +
          '/update-location',
        position
      )
      .subscribe((response: any) => {
        //console.log(response);
      });
  }
}
