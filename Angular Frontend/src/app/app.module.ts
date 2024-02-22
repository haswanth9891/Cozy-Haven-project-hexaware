import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { HotelownerRegisterComponent } from './components/hotelowner-register/hotelowner-register.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { HotelownerDashboardComponent } from './components/hotelowner-dashboard/hotelowner-dashboard.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';
import { BookingComponent } from './components/booking/booking.component';
import { BookingListComponent } from './components/booking-list/booking-list.component';
import { HotelownerListComponent } from './components/hotelowner-list/hotelowner-list.component';
import { UsersListComponent } from './components/users-list/users-list.component';
import { RoomComponent } from './components/room/room.component';
import { HotelComponent } from './components/hotel/hotel.component';
import { AboutComponent } from './components/about/about.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    UserRegisterComponent,
    LoginComponent,
    HotelownerRegisterComponent,
    AdminDashboardComponent,
    HotelownerDashboardComponent,
    UserDashboardComponent,
    BookingComponent,
    BookingListComponent,
    HotelownerListComponent,
    UsersListComponent,
    RoomComponent,
    HotelComponent,
    AboutComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
