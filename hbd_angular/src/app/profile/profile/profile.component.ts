import { HttpClient, HttpContext, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, of, pipe, tap } from 'rxjs';

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
  submitted = false;
  
  constructor( public fb: FormBuilder, private http: HttpClient ) {

    this.form = this.fb.group({
      id: [''],
      photo: [null, [Validators.required]]
    });

  }

  ngOnInit(): void {
    this.switchOffAlert();
    this.displayPhoto();
  }

  displayPhoto() {
    this.photoUrl = localStorage.getItem("staffPhotoUrl")!;
  }

  switchOffAlert() {
    this.successAlert = false;
  }

  uploadFile( event: Event ) {
    const file = (event.target as HTMLInputElement).files![0];
    console.log(file.type);
    this.submitted = true;

    if( (file.type == "image/jpeg") ) {
      this.form.patchValue({
        photo: file,
      });
      this.form.get('photo')!.updateValueAndValidity();
    } else {
      this.form.reset();
      this.form.controls["photo"].setValidators([Validators.required]);
      this.form.get('photo')!.updateValueAndValidity();
    }

  }

  submitForm() {
    var formData: any = new FormData();
    formData.append('id', localStorage.getItem("staffId")!);
    formData.append('photo', this.form.get('photo')!.value);
    this.http.patch<string>('http://localhost:2020/staff/photo', formData)
                .subscribe( data => {
                  var url = "http://localhost:2020/staffPhotos/"+localStorage.getItem("staffId")!+"/photo.jpg?date="+new Date();
                  console.log(url);
                  localStorage.setItem("staffPhotoUrl", url);
                  this.displayPhoto();
                  this.successAlert = true;
                });
  }

}
