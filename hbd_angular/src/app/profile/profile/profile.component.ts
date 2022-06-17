import { HttpClient, HttpContext, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map, Observable, of, pipe, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  successAlert = false;
  public staffExist:boolean = true;
  public photoUrl!: string;
  public form!: FormGroup;
  isUserRegistered = false;
  submitted = false;
  //Errors
  errorAlert = false;
  serverError = false;
  staffIdError = false;

  constructor( public fb: FormBuilder, private http: HttpClient, private router: Router ) {

    this.form = this.fb.group({
      id: ['', [Validators.required]],
      name: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.required]],
      photo: [null, [Validators.required]]
    });

  }

  ngOnInit(): void {
    if ( localStorage.getItem('isUserLoggedIn') == "true" ) {
      this.isUserRegistered = true;
    }
    this.switchOffAlerts();
    this.displayPhoto();
  }

  displayPhoto() {
    this.photoUrl = localStorage.getItem("staffPhotoUrl")!;
  }

  switchOffAlerts() {
    this.successAlert = false;
    this.errorAlert = false;
    this.serverError = false;
    this.staffIdError = false;
  }

  uploadFile( event: Event ) {
    const file = (event.target as HTMLInputElement).files![0];
    console.log(file.type);
    this.submitted = true;
    console.log("Profile Component, Is user registered ? "+this.isUserRegistered);

    if( (file.type == "image/jpeg") ) {
      if( !this.isUserRegistered ){
        this.form.patchValue({
          photo: file,
        });
        this.form.get('photo')!.updateValueAndValidity();
      } else {
        this.form.patchValue({
          id: localStorage.getItem("staffId")!,
          name: localStorage.getItem("staffName")!,
          lastname: localStorage.getItem("staffLastname")!,
          email: localStorage.getItem("staffEmail")!,
          photo: file,
        });
        this.form.get('id')!.updateValueAndValidity();
        this.form.get('name')!.updateValueAndValidity();
        this.form.get('lastname')!.updateValueAndValidity();
        this.form.get('email')!.updateValueAndValidity();
        this.form.get('photo')!.updateValueAndValidity();
      }

      
    } else {
      this.form.reset();
      this.form.controls["photo"].setValidators([Validators.required]);
      this.form.get('photo')!.updateValueAndValidity();
    }

  }

  submitForm() {
    var formData: any = new FormData();
    var loginState:string = localStorage.getItem("isUserLoggedIn")!;
    
  //  if ( loginState == "true" ){
    if ( this.isUserRegistered ){

      formData.append('id', localStorage.getItem("staffId")!);
      formData.append('photo', this.form.get('photo')!.value);
      this.http.patch<string>(environment.backEndUrl+'/staff/photo', formData)
                  .subscribe( data => {
                    var url = environment.backEndUrl+"/staffPhotos/"+localStorage.getItem("staffId")!+".jpg?date="+new Date();
                    console.log(url);
                    localStorage.setItem("staffPhotoUrl", url);
                    this.displayPhoto();
                    this.successAlert = true;
                  });

  //  } else if ( loginState == "partially" ) {
    } else {
      
      const staffId = this.form.get('id')!.value;
      const staffName = this.form.get('name')!.value;
      const staffLastname = this.form.get('lastname')!.value;
      const staffEmail = this.form.get('email')!.value;

      formData.append('id', this.form.get('id')!.value);
      formData.append('name', this.form.get('name')!.value);
      formData.append('lastname', this.form.get('lastname')!.value);
      formData.append('email', this.form.get('email')!.value);
      formData.append('photo', this.form.get('photo')!.value);
      formData.append('username', localStorage.getItem("staffUsername")!);
      this.http.post<string>(environment.backEndUrl+'/staff', formData)
                  .subscribe( data => {


                    var url = environment.backEndUrl+"/staffPhotos/"+localStorage.getItem("staffId")!+".jpg?date="+new Date();
                    localStorage.setItem("staffPhotoUrl", url);
                    this.successAlert = true;
                    this.isUserRegistered = true;
                      localStorage.setItem('isUserLoggedIn', "true");
                      localStorage.setItem('staffId', staffId );
                      localStorage.setItem('staffName', staffName );
                      localStorage.setItem('staffLastname', staffLastname );
                      localStorage.setItem('staffEmail', staffEmail );
                        var url = environment.backEndUrl+"/staffPhotos/"+staffId+".jpg?date="+new Date();
                      localStorage.setItem("staffPhotoUrl", url);
                      console.log("Profile Component, Set Local Storage STaffId : "+ localStorage.getItem('staffId') );
                    this.displayPhoto();
                    this.router.navigate(['/']);


                  }, err => {

                    switch(err.status) {
                      case 0: //Unable to contact server
                        this.errorAlert = true;
                        this.serverError = true;
                        break;
                      case 409: //User does not exist on application database
                        this.errorAlert = true;
                        this.staffIdError = true;
                        break;
                    }

                  });

    }

    
  }

}
