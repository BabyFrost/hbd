import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { Staff } from '../../model/staff';

@Component({
  selector: 'app-staff-form',
  templateUrl: './staff-form.component.html',
  styleUrls: ['./staff-form.component.css']
})
export class StaffFormComponent implements OnInit {

  public staffExist:boolean = false;
  public photoUrl!: string;
  public form!: FormGroup;
  constructor( public fb: FormBuilder, private http: HttpClient ) {

    this.form = this.fb.group({
      id: [''],
      name: [''],
      email: [''],
      photo: [null]
    });

  }

  ngOnInit(): void {
  }

  displayStaff( event:Event ) {
    // console.log("Staff Id : "+(event.target as HTMLInputElement).value );

    this.form.patchValue({
      name: '',
      email: '',
      photo: null,
    });
    this.form.get('name')!.updateValueAndValidity();
    this.form.get('email')!.updateValueAndValidity();
    this.form.get('photo')!.updateValueAndValidity();

    this.staffExist = false;
    var staff:Staff;
    this.http.get<Staff[]>('http://localhost:2020/staff?id='+(event.target as HTMLInputElement).value ).subscribe((data) => {
      console.log(data);
      const res = data;
      console.log(res);
      staff = res[0];
      this.photoUrl = staff.photoUrl;

      this.staffExist = true;
      console.log("Staff exists");

      this.form.patchValue({
        name: staff.name,
        email: staff.email
      });
      this.form.get('name')!.updateValueAndValidity();
      this.form.get('email')!.updateValueAndValidity(); 

    }); 

//    this.photoUrl = this.http.get<string>('http://localhost:2020/staff').subscribe( photoUrl => this.photoUrl = photoUrl );
  }

  uploadFile( event: Event ) {
    const file = (event.target as HTMLInputElement).files![0];
    this.form.patchValue({
      photo: file,
    });
    this.form.get('photo')!.updateValueAndValidity();
  }

  
  submitForm() {
    var formData: any = new FormData();
    formData.append('id', this.form.get('id')!.value);
    formData.append('name', this.form.get('name')!.value);
    formData.append('email', this.form.get('email')!.value);
    formData.append('photo', this.form.get('photo')!.value);
    this.http
      .post('http://localhost:2020/staff', formData)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) => console.log(error),
      });
    
    //this.displayStaff;
  }

}
