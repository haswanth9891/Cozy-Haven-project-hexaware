import { ChangeDetectorRef, Component } from '@angular/core';
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
      userFirstName: ['', Validators.required],
      userLastName: ['', Validators.required],
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

      userFirstName: this.signupForm.value.userFirstName,
      userLastName: this.signupForm.value.userLastName,
      username: this.signupForm.value.username,
      contactNumber: this.signupForm.value.contactNumber,
      password: this.signupForm.value.password,
      email: this.signupForm.value.email,
      gender: this.signupForm.value.gender,
      address: this.signupForm.value.address

    };

    this.userService.postUser(newUser)
      .subscribe(
        user => {
          console.log('Inserted:', user);

          this.signupForm.reset();
          sessionStorage.setItem('userFirstName', user.userFirstName);
          alert('Registered successfully!');
          this.router.navigate(['/login']);

        },
        error => {
          console.log('Error:', error);
          const errorMessage = error.message || 'An error occurred. Please try again later.';
          alert(errorMessage);
        }
      );

  }



  isFieldValid(field: string) {
    const control = this.signupForm.get(field);
    return !control?.valid && control?.touched;
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
