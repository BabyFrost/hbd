import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { Staff } from './model/staff';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'hbd';
  isUserLoggedIn = false;
  staff!:Staff;

  constructor(private authService: AuthService, private router: Router) {
    router.events
      .pipe( filter(e => e instanceof NavigationEnd) )
      .subscribe( (e) => {
        this.checkAccess();
      });
  }

  ngOnInit() {
    this.checkAccess();
  }

  checkAccess() {
    let storeData = localStorage.getItem("isUserLoggedIn");

    if( storeData != null && storeData == "true") {
      this.isUserLoggedIn = true;
      this.staff = new Staff( localStorage.getItem("staffId")!, 
                              localStorage.getItem("staffName")!,
                              localStorage.getItem("staffLastName")!,
                              localStorage.getItem("staffEmail")!, 
                              localStorage.getItem("staffPhotoUrl")! );
    } else {
      this.isUserLoggedIn = false;
    }
       
  }

}
