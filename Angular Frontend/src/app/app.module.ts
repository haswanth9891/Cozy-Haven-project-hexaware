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

import { AboutComponent } from './components/about/about.component';
import { ProfileComponent } from './components/profile/profile.component';
import { HotelownerLoginComponent } from './components/hotelowner-login/hotelowner-login.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { OurHotelsComponent } from './components/our-hotels/our-hotels.component';
import { HotelListComponent } from './components/hotel-list/hotel-list.component';
import { UserService } from './services/user.service';
import { HotelownerService } from './services/hotelowner.service';
import { HotelService } from './services/hotel.service';
import { RoomService } from './services/room.service';
import { SearchRoomsListComponent } from './components/search-rooms-list/search-rooms-list.component';
import { ManageHotelownersComponent } from './components/manage-hotelowners/manage-hotelowners.component';

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

    AboutComponent,
    ProfileComponent,
    HotelownerLoginComponent,
    AdminLoginComponent,
    OurHotelsComponent,
    HotelListComponent,
    SearchRoomsListComponent,
    ManageHotelownersComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule

  ],
  providers: [UserService, HotelownerService, HotelService, RoomService],
  bootstrap: [AppComponent]
})
export class AppModule { }
