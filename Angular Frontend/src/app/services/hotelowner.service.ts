import { Injectable } from '@angular/core';
import { HotelOwner } from '../model/hotelowner';
import { Observable, catchError, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HotelownerService {

  constructor(private http: HttpClient) { }

  private baseUrl = 'https://localhost:8081/api/hotelowner';


  postHotelOwner(hotelOwner: HotelOwner): Observable<HotelOwner> {
    const url = `${this.baseUrl}/register`;
    return this.http.post<HotelOwner>(url, hotelOwner)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 409) {
      return throwError('Conflict: User already exists');
    }
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError('An error occurred. Please try again later.');
  }
}
