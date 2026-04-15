import { Routes } from '@angular/router';
import { LandingPageComponent } from './features/home/pages/landing-page/landing-page.component';
import { LoginComponent } from './features/auth/login/login.component';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: '', component: LandingPageComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
];

