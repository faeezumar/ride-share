import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-driver-dashboard',
  templateUrl: './driver-dashboard.component.html',
  styleUrls: ['./driver-dashboard.component.css']
})
export class DriverDashboardComponent {
  isAvailable: boolean = false; 
  latitude: number = 0;
  longitude: number = 0;
  geoLocationUrl: string = "http://localhost:8080/api/v1/drivers/${driverId}/update-location"
  sendLocation: boolean = false;
  driverId: any = ''

  constructor(
    private httpClient: HttpClient,
    private route: ActivatedRoute
  ) { }


  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.driverId = params.get('id');
    });
  }

  toggleAvailability() {
    this.sendLocation = !this.sendLocation
    let options = { enableHighAccuracy: true, maximumAge: 150000, timeout: 30000 };
    if (navigator.geolocation && this.sendLocation) {
      navigator.geolocation.watchPosition((position) => {
        let location = {
          latitude: position['coords']['latitude'],
          longitude: position['coords']['longitude'],
        };
        console.log(location)
        this.postDriverLocation(location)
      }), options;
    }
  }

  postDriverLocation(position: any) {
    this.httpClient.post(this.geoLocationUrl, position)
    .subscribe((response: any) => {
      console.log(response)
    })
  }

}