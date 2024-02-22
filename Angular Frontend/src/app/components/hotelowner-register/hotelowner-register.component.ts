import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HotelOwner } from 'src/app/model/hotelowner';
import { HotelownerService } from 'src/app/services/hotelowner.service';

@Component({
  selector: 'app-hotelowner-register',
  templateUrl: './hotelowner-register.component.html',
  styleUrls: ['./hotelowner-register.component.css']
})
export class HotelownerRegisterComponent {
  signupForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private hotelownerService: HotelownerService,
    private router: Router
  ) {
    this.signupForm = this.formBuilder.group({
      hotelOwnerName: ['', Validators.required],


      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      gender: ['', Validators.required],
      address: ['', Validators.required],
      hotelName: ['', Validators.required],
      location: ['', Validators.required],
      hasDining: ['', Validators.required],
      hasParking: ['', Validators.required],
      hasFreeWiFi: ['', Validators.required],
      hasRoomService: ['', Validators.required],
      hasSwimmingPool: ['', Validators.required],
      hasFitnessCenter: ['', Validators.required]


    });

    if (this.signupForm) {
      this.signupForm.valueChanges.subscribe(() => {
        this.onFormValueChanged();
      });
    }
  }

  insertUser() {
    if (this.signupForm.invalid) {
      return;
    }

    const newHotelOwner: HotelOwner = {

      hotelOwnerName: this.signupForm.value.hotelOwnerName,
      username: this.signupForm.value.username,
      password: this.signupForm.value.password,
      email: this.signupForm.value.email,
      gender: this.signupForm.value.gender,
      address: this.signupForm.value.address,
      hotelName: this.signupForm.value.hotelName,
      location: this.signupForm.value.location,
      hasDining: this.signupForm.value.hasDining,
      hasParking: this.signupForm.value.hasParking,
      hasFreeWiFi: this.signupForm.value.hasFreeWiFi,
      hasRoomService: this.signupForm.value.hasRoomService,
      hasSwimmingPool: this.signupForm.value.hasSwimmingPool,
      hasFitnessCenter: this.signupForm.value.hasFitnessCenter

    };

    this.hotelownerService.postHotelOwner(newHotelOwner)
      .subscribe(
        hotelowner => {
          console.log('Inserted:', hotelowner);

          this.signupForm.reset();

          // Save the first name in session storage
          sessionStorage.setItem('hotelOwnerName', hotelowner.hotelOwnerName);

          alert('Registered successfully!');

          this.router.navigate(['/profile']);
        },
        error => {
          console.log('Error:', error);
        }
      );
  }



  isFieldValid(field: string) {
    return (
      !this.signupForm.get(field)?.valid &&
      this.signupForm.get(field)?.touched
    );
  }


  getErrorMessage(field: string) {
    if (this.signupForm.get(field)?.hasError('required')) {
      return 'This field is required';
    }
    return '';
  }

  onFormValueChanged() {
    for (const field in this.signupForm.controls) {
      if (this.signupForm.controls.hasOwnProperty(field)) {
        this.signupForm.controls[field].markAsTouched();
      }
    }
  }

}
