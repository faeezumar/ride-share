import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { GoogleMapsModule } from '@angular/google-maps'

import { AppComponent } from './app.component';
import { RideBookingComponent } from './ride-booking/ride-booking.component';
import { DriverRegistrationComponent } from './driver-registration/driver-registration.component';
import { DriverDashboardComponent } from './driver-dashboard/driver-dashboard.component';
import { CustomerDashboardComponent } from './customer-dashboard/customer-dashboard.component';
import { HomeComponentComponent } from './home-component/home-component.component';
import { CustomerRegistrationComponent } from './customer-registration/customer-registration.component';
import { SseComponentComponent } from './sse-component/sse-component.component';

const appRoutes: Routes = [
  {path: '',  component: HomeComponentComponent},
  {path: 'Home', component: HomeComponentComponent},
  {path: 'CustomerRegistration', component: CustomerRegistrationComponent},
  {path: 'CustomerDashboard/:id', component: CustomerDashboardComponent},
  {path: 'DriverRegistration', component: DriverRegistrationComponent},
  {path: 'DriverDashboard/:id', component: DriverDashboardComponent},
  {path: 'RideRequest/:id', component: RideBookingComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    RideBookingComponent,
    DriverRegistrationComponent,
    DriverDashboardComponent,
    CustomerDashboardComponent,
    HomeComponentComponent,
    CustomerRegistrationComponent,
    SseComponentComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    GoogleMapsModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
