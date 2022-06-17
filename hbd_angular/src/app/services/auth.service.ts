import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Observable, of } from 'rxjs';
import { tap, delay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Staff } from '../model/staff';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  staff!:Staff;
  isUserLoggedIn: boolean = false;

  login(formData: FormData): Observable<any> {

    this.isUserLoggedIn = false;

    return this.http.post<Staff>( environment.backEndUrl+'/auth', formData ).pipe(tap(data => {
      this.staff = data;
      this.isUserLoggedIn = true;
      localStorage.setItem('isUserLoggedIn', "true");
      localStorage.setItem('staffId', this.staff.id);
      localStorage.setItem('staffName', this.staff.name);
      localStorage.setItem('staffLastname', this.staff.lastname);
      localStorage.setItem('staffEmail', this.staff.email);
      localStorage.setItem('staffPhotoUrl', this.staff.photoUrl);

      return of(this.isUserLoggedIn).pipe( tap(val => { 
          console.log("Is User Authentication is successful : " + val); 
        })
      );
    }));
  }

  logout(): void {
    this.isUserLoggedIn = false;
    localStorage.clear();
  }

  constructor( private http: HttpClient ) { }
}
