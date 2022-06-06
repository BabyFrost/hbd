import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class StaffGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(next:ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    let url: string = state.url;
    console.log( "Can Activate : "+this.checkLogin(url) );
    return this.checkLogin(url);
  }

  checkLogin(url: string): true | UrlTree {
    console.log("staffGuard Url: " + url)
    let val: string = localStorage.getItem('isUserLoggedIn')!;
    console.log("staffGuard Val: " + val)
    if(val != null && val == "true"){
      if(url == "/login") {
        return this.router.parseUrl('/staff');
      } else { 
        return true;
      }
    } else {
      return this.router.parseUrl('/login');
    }
  }

}
