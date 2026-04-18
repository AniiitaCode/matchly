import { Routes } from '@angular/router';
import { LandingPageComponent } from './features/home/pages/landing-page/landing-page.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { HomeComponent } from './features/home/pages/home/home.component';
import { EditProfileComponent } from './features/profile/pages/edit-profile/edit-profile.component';
import { EditDatingProfileComponent } from './features/profile/pages/edit-dating-profile/edit-dating-profile.component';
import { EditPersonalityProfileComponent } from './features/profile/pages/edit-personality-profile/edit-personality-profile.component';
import { SearchComponent } from './features/search/pages/search/search.component';
import { UserDetailComponent } from './features/profile/pages/user-detail/user-detail.component';
import { MatchComponent } from './features/match/pages/matches/match.component';
import { RejectedMatchesComponent } from './features/match/pages/rejected-matches/rejected-matches.component';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: '', component: LandingPageComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [authGuard] },

  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'profile', component: EditProfileComponent, canActivate: [authGuard] },
  { path: 'profile-dating', component: EditDatingProfileComponent, canActivate: [authGuard] },
  { path: 'profile-personality', component: EditPersonalityProfileComponent, canActivate: [authGuard] },
  { path: 'home-search', component: SearchComponent, canActivate: [authGuard] },
  { path: 'user/:id', component: UserDetailComponent, canActivate: [authGuard] },
  { path: 'matches', component: MatchComponent, canActivate: [authGuard] },
  { path: 'matches/rejected', component: RejectedMatchesComponent, canActivate: [authGuard] },
];

