import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { UserRegisterComponent } from './components/user-register/user-register.component';
import { LoginComponent } from './components/login/login.component';
import { HotelownerRegisterComponent } from './components/hotelowner-register/hotelowner-register.component';
import { HotelComponent } from './components/hotel/hotel.component';
import { AboutComponent } from './components/about/about.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { HotelownerDashboardComponent } from './components/hotelowner-dashboard/hotelowner-dashboard.component';
import { UserDashboardComponent } from './components/user-dashboard/user-dashboard.component';

import { ProfileComponent } from './components/profile/profile.component';
import { HotelownerLoginComponent } from './components/hotelowner-login/hotelowner-login.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'header', component: HeaderComponent },
  { path: 'home', component: HomeComponent },
  { path: 'footer', component: FooterComponent },
  { path: 'user-register', component: UserRegisterComponent },
  { path: 'hotelowner-register', component: HotelownerRegisterComponent },
  { path: 'hotel', component: HotelComponent },
  { path: 'about', component: AboutComponent },

  { path: 'login', component: LoginComponent },
  { path: 'hotelowner-login', component: HotelownerLoginComponent },
  { path: 'admin-login', component: AdminLoginComponent },

  { path: 'user-dashboard', component: UserDashboardComponent },
  { path: 'admindashboard', component: AdminDashboardComponent },
  { path: 'hotelowner-dashboard', component: HotelownerDashboardComponent },
  { path: "profile", component: ProfileComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
