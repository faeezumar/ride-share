import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SseService } from '../sse.service';
import { Subscription } from 'rxjs';

interface RideRequest {
  id: number;
  pickupLocation: string;
  destination: string;
  time: string;
}

export class RideNotification {
  pickupLocation: string;
  destination: string;
  time: string;
  status: string;

  constructor(
    pickupLocation: string, 
    destination: string, 
    time: string,
    status: string
    ) {
    this.pickupLocation = pickupLocation;
    this.destination = destination;
    this.time = time;
    this.status = status
  }
}

@Component({
  selector: 'app-sse-component',
  templateUrl: './sse-component.component.html',
  styleUrls: ['./sse-component.component.css'],
})
export class SseComponentComponent implements OnInit, OnDestroy {
  eventData:  RideNotification = new RideNotification('', '', '', '');
  private sseSubscription: Subscription = new Subscription();
  disableButton: boolean = false;
  private customerNotificationUrl = "http://localhost:8080/api/v1/notifications/customers/subscribe"
  private driverNotificationUrl = "http://localhost:8080/api/v1/notifications/drivers/subscribe"
  private acceptRequestUrl = "http://localhost:8080/api/v1/notifications/notify-customers"
  @Input() userType: any;

  constructor(
    private sseService: SseService,
    private httpClient: HttpClient,
    ) {}

  ngOnInit(): void {
    this.sseSubscription = this.sseService.connect(
      this.userType == 'customer' 
      ? this.customerNotificationUrl 
      : this.driverNotificationUrl
    ).subscribe(
      (event: MessageEvent) => {
        this.eventData = JSON.parse(event.data);
      },
      (error) => {
        console.error('Error with SSE connection:', error);
      }
    );
  }

  ngOnDestroy(): void {
    this.sseSubscription.unsubscribe();
    this.sseService.disconnect();
  }

  acceptRequest(request: any) {
    this.disableButton = true
    this.httpClient.post(this.acceptRequestUrl, { request })
      .subscribe(
        (response: any) => {
          console.log("Request Accepted.")
        },
        (error: any) => {
          console.error('Error accepting ride request:', error);
        }
      );
  }

  rejectRequest(request: any) {
    this.disableButton
    this.httpClient.post('/api/v1/reject-request', { request })
      .subscribe(
        (response: any) => {
          console.log('Request rejected')
        },
        (error: any) => {
          console.error('Error rejecting ride request:', error);
        }
      );
  }
}