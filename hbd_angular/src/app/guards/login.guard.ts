import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(next:ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    let url: string = state.url;
    console.log("Login Guard : Going to : "+url)
    return this.checkLogin(url);
  }

  checkLogin(url: string): true | UrlTree {
    let val: string = localStorage.getItem('isUserLoggedIn')!;
    if(val != null && val == "true"){
      return this.router.parseUrl('/profile');
    } else {
      return true;
    }
  }
  
}
