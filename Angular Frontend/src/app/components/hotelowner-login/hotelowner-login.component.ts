import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthRequest } from 'src/app/model/authRequest';
import { JwtClientHotelownerService } from 'src/app/services/jwt-client-hotelowner.service';

@Component({
  selector: 'app-hotelowner-login',
  templateUrl: './hotelowner-login.component.html',
  styleUrls: ['./hotelowner-login.component.css']
})
export class HotelownerLoginComponent {

  response: any;
  token: any;

  authRequest: AuthRequest = new AuthRequest();

  constructor(private jwtService: JwtClientHotelownerService, private router: Router) { }






  readFormData(formData: any) {

    this.authRequest.username = formData.form.value.username;
    this.authRequest.password = formData.form.value.password;

    this.getAccessToken(this.authRequest);

  }

  public getAccessToken(authRequest: any) {

    let response = this.jwtService.getGeneratedToken(authRequest);

    response.subscribe((genToken) => {
      this.token = genToken; console.log(genToken);
      this.accessApi(this.token)
    });

  }

  // public accessApi(token:any){

  //   let responseBody =    this.jwtService.authorizationTest(token);

  //       console.log(responseBody )


  //   }

  public accessApi(token: any): void {
    const decodedToken = this.jwtService.authorizationTest(token);

    if (decodedToken) {
      console.log('Decoded Token Claims:', decodedToken);

      const role = decodedToken.role;
      const hotelOwnerId = decodedToken.customerId;

      console.log(role);
      console.log(hotelOwnerId);
      if (role === 'HOTEL_OWNER') {
        console.log('Navigating to user-dashboard...');
        this.router.navigate(['/hotelowner-dashboard']);
      }

      else {
        console.log('Permission denied. No navigation.');
      }


    } else {
      console.error('Error accessing API');
    }
  }

}
