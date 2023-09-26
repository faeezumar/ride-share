import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SseService } from '../sse.service';
import { Subscription } from 'rxjs';

interface RideRequest {
  id: number;
  customerId: number;
  driverId: number;
  pickupLocation: string;
  destination: string;
  time: string;
  status: string;
}

@Component({
  selector: 'app-sse-component',
  templateUrl: './sse-component.component.html',
  styleUrls: ['./sse-component.component.css'],
})
export class SseComponentComponent implements OnInit, OnDestroy {
  notification!: RideRequest;
  private sseSubscription: Subscription = new Subscription();
  disableButton: boolean = false;
  @Input() userType: any;
  @Input() userId: any;

  private acceptRequestUrl = 'http://localhost:8080/api/v1/trip/accept-trip';

  constructor(private sseService: SseService, private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.sseSubscription = this.sseService
      .connect(
        this.userType == 'customer'
          ? 'http://localhost:8080/api/v1/notifications/customers/' +
              this.userId +
              '/subscribe'
          : 'http://localhost:8080/api/v1/notifications/drivers/' +
              this.userId +
              '/subscribe'
      )
      .subscribe({
        next: (event: MessageEvent) => {
          this.notification = JSON.parse(event.data);
        },
      });
  }

  ngOnDestroy(): void {
    this.sseSubscription.unsubscribe();
    this.sseService.disconnect();
  }

  acceptRequest(request: RideRequest) {
    console.log(request);
    this.disableButton = true;
    this.httpClient
      .post(this.acceptRequestUrl, {
        ...request,
        tripStatus: 'ACCEPTED',
      })
      .subscribe(
        (response: any) => {
          console.log('Request Accepted.');
        },
        (error: any) => {
          console.error('Error accepting ride request:', error);
        }
      );
  }

  rejectRequest(request: RideRequest) {
    this.disableButton;
    this.httpClient.post('/api/v1/reject-request', { request }).subscribe(
      (response: any) => {
        console.log('Request rejected');
      },
      (error: any) => {
        console.error('Error rejecting ride request:', error);
      }
    );
  }
}
