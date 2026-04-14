import { Injectable, signal, computed, inject } from '@angular/core';
import { Observable, tap, of } from 'rxjs';
import { ApiService } from './api.service';
import { AuthUser, LoginCredentials, LoginResponse, RegisterCredentials, RegisterResponse } from '../interfaces/auth.interface';
import { User } from '../../shared/interfaces/user.interface';
import { UserViewDto } from '../../features/profile/interfaces/user-view.interface';

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  private user = signal<User | null>(null);
  private authUser = signal<AuthUser | null>(null);

  isLoggedIn = computed(() => this.authUser() !== null);
  currentUser = computed(() => this.user());

  private api = inject(ApiService);

  getLoginPage(): Observable<LoginResponse> {
    return this.api.get<LoginResponse>('login');
  }

  login(credentials: LoginCredentials): Observable<LoginResponse> {
    return this.api.post<LoginResponse>('login', credentials, { withCredentials: true }).pipe(
      tap(response => {
        if (response.success) {
          this.user.set({ username: credentials.username } as User);
        }
      })
    );
  }

  getRegisterPage(): Observable<RegisterResponse> {
    return this.api.get<RegisterResponse>('register');
  }

  register(credentials: RegisterCredentials): Observable<RegisterResponse> {
    return this.api.post<RegisterResponse>('register', credentials);
  }

  getCurrentUser(): Observable<UserViewDto> {
    return this.api.get<UserViewDto>('user/me', { withCredentials: true });
  }

  restoreUser(): Observable<UserViewDto> {
    if (this.authUser()) {
      return of(this.authUser() as unknown as UserViewDto);
    }

    return this.getCurrentUser().pipe(
      tap(dto => {
        this.authUser.set({
          id: dto.id,
          username: dto.username,
          firstName: dto.firstName
        });
      })
    );
  }

  logout(): Observable<void> {
    return this.api.post<void>('logout', {}, { withCredentials: true })
      .pipe(
        tap(() => {
          this.user.set(null);
          this.authUser.set(null);
        })
      )
  }

}
