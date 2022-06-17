import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;
  formGroup!: FormGroup;
  errorAlert = false;
  serverError = false;
  credentialsError = false;

  constructor( private authService : AuthService, private router : Router ) { }

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl("admin"),
      password: new FormControl("admin"),
    });
  }

  switchOffAlerts() {
    this.errorAlert = false;
    this.serverError = false;
    this.credentialsError = false;
  }

  onClickSubmit(data: any) {
    this.switchOffAlerts();
    this.username = data.username;
    this.password = data.password;

    var formData: any = new FormData();
    formData.append('login', this.username);
    formData.append('password', this.password);
    
    this.authService.login(formData)
      .subscribe( 
        data => { 
          console.log("Is Login Success: " + data); 
          if(data) this.router.navigate(['/profile']); 
        },
        err => {
          
          switch(err.status) {
            case 0: //Unable to contact server
              this.errorAlert = true;
              this.serverError = true;
              break;
            case 400: //bad login or password
              this.errorAlert = true;
              this.credentialsError = true;
              break;
            case 404: //User does not exist on application database
              this.errorAlert = true;
              this.credentialsError = true;
              localStorage.setItem('staffUsername', this.username );
              localStorage.setItem('isUserLoggedIn', "partially");
              this.router.navigate(['/profile'])
              break;

          }

          console.log( err.status );
          console.log( err.message );
        });
  }

}
