import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {


  signupForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.signupForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      contactNumber: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      gender: ['', Validators.required],
      address: ['', Validators.required]


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

    const newUser: User = {

      firstName: this.signupForm.value.firstName,
      lastName: this.signupForm.value.lastName,
      username: this.signupForm.value.username,
      contactNumber: this.signupForm.value.contactInfo,
      password: this.signupForm.value.password,
      email: this.signupForm.value.email,
      gender: this.signupForm.value.gender,
      address: this.signupForm.value.address

    };

    this.userService.postCustomer(newUser)
      .subscribe(
        user => {
          console.log('Inserted:', user);

          this.signupForm.reset();

          // Save the first name in session storage
          sessionStorage.setItem('firstName', user.firstName);

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
