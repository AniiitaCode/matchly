import { Routes } from '@angular/router';
import { LandingPageComponent } from './features/home/pages/landing-page/landing-page.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { HomeComponent } from './features/home/pages/home/home.component';
import { EditProfileComponent } from './features/profile/pages/edit-profile/edit-profile.component';
import { EditDatingProfileComponent } from './features/profile/pages/edit-dating-profile/edit-dating-profile.component';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: '', component: LandingPageComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [authGuard] },

  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'profile', component: EditProfileComponent, canActivate: [authGuard] },
  { path: 'profile-dating', component: EditDatingProfileComponent, canActivate: [authGuard] },
];

