import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {

  users!: any[];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/api/users').subscribe(data => {
      this.users = data;
    });
  }

  // Method to delete a user by ID
  deleteUser(userId: number): void {
    // Implement the logic to delete the user with the specified ID
    // You may need to make an HTTP request to the server for deletion
  }


}
