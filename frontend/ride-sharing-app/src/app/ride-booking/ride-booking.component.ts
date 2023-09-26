import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { GoogleMap } from '@angular/google-maps';
import { ViewChild } from '@angular/core';

declare var place1: any;
declare var place2: any;

@Component({
  selector: 'app-ride-booking',
  templateUrl: './ride-booking.component.html',
  styleUrls: ['./ride-booking.component.css'],
})
export class RideBookingComponent {
  formData: any = {};
  customerId: any;
  private rideRequestUrl: string =
    'http://localhost:8080/api/v1/trip/request-trip';
  mapOptions: google.maps.MapOptions = {
    center: { lat: 38.9987208, lng: -77.2538699 },
    zoom: 14,
    disableDefaultUI: true,
  };

  @ViewChild(GoogleMap) map!: GoogleMap;
  markers: any = [];

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.customerId = params.get('id');
    });
  }

  submitForm() {
    setTimeout(() => {
      this.httpClient
        .post(this.rideRequestUrl, {
          customerId: this.customerId,
          pickupLocation: place1.name,
          destination: place2.name,
          longitude: place1.geometry['location'].lng(),
          latitude: place1.geometry['location'].lat(),
          tripStatus: 'PENDING',
        })
        .subscribe((response: any) => {});
    }, 2000);
    this.router.navigate(['/CustomerDashboard', this.customerId]);
  }

  ngAfterViewInit() {
    const bounds = this.getBounds(this.markers);
    //this.map.googleMap!.fitBounds(bounds);
  }

  onPicupLocationChange(event: any) {
    setTimeout(() => this.updateMarkers(), 2000);
  }

  getBounds(markers: any) {
    let north;
    let south;
    let east;
    let west;

    for (const marker of markers) {
      north =
        north !== undefined
          ? Math.max(north, marker.position.lat)
          : marker.position.lat;
      south =
        south !== undefined
          ? Math.min(south, marker.position.lat)
          : marker.position.lat;
      east =
        east !== undefined
          ? Math.max(east, marker.position.lng)
          : marker.position.lng;
      west =
        west !== undefined
          ? Math.min(west, marker.position.lng)
          : marker.position.lng;
    }

    const bounds = { north, south, east, west };
    return bounds;
  }

  updateMarkers() {
    this.markers = [
      {
        position: {
          lat: place1.geometry['location'].lat(),
          lng: place1.geometry['location'].lng(),
        },
      },
      {
        position: {
          lat: place2.geometry['location'].lat(),
          lng: place2.geometry['location'].lng(),
        },
      },
    ];
    const bounds = this.getBounds(this.markers);
    this.map.googleMap!.fitBounds(bounds);
  }
}
