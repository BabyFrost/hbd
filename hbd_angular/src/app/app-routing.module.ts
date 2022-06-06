import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { StaffFormComponent } from './forms/staff-form/staff-form.component';
import { LoginComponent } from './forms/login/login.component';
import { LogoutComponent } from './forms/logout/logout.component';

import { StaffGuard } from './guards/staff.guard';
import { ProfileComponent } from './profile/profile/profile.component';
import { ProfileGuard } from './guards/profile.guard';
import { LoginGuard } from './guards/login.guard';

const routes: Routes = [
   { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
   { path: 'logout', component: LogoutComponent },
   { path: 'staff', component: StaffFormComponent, canActivate: [StaffGuard]},
   { path: 'profile', component: ProfileComponent, canActivate: [ProfileGuard]},
   { path: '', redirectTo: 'profile', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }