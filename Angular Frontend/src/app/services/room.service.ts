import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SearchDTO } from '../model/searchDTO';
import { Room } from '../model/room';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private apiUrl = 'http:/localhost/api/room'; // Replace with your actual API URL

  constructor(private http: HttpClient) { }

  searchRooms(searchDTO: any): Observable<Room[]> {
    // Make the HTTP request to your backend
    return this.http.get<Room[]>(`${this.apiUrl}/search`, { params: searchDTO });
  }
}
