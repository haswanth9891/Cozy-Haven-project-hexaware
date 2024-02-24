import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  constructor(private http: HttpClient) { }

  private apiUrl = 'http://localhost:8081/api/hotel';

  getAllHotels(): Observable<any[]> {
    const url = `${this.apiUrl}/getall`; // Update with your actual endpoint
    return this.http.get<any[]>(url);
  }


}
