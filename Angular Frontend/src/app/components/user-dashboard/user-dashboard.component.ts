import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  searchForm!: FormGroup;
  searchResults: any[] = [];

  constructor(private fb: FormBuilder, private roomService: RoomService, private router: Router) { } // Inject RoomService

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      checkin: ['', Validators.required],
      checkout: ['', Validators.required],
      destination: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.searchForm.valid) {
      const searchDTO = this.searchForm.value;
      this.roomService.searchRooms(searchDTO).subscribe(
        (data) => {
          this.searchResults = data;
          console.log('Search Results:', this.searchResults);
          this.router.navigate(['/search-rooms-list'], { state: { results: this.searchResults } });
        },
        (error) => {
          console.error('Error fetching search results:', error);
        }
      );
    }
  }


}
