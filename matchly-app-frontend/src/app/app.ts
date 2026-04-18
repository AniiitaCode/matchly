import { Component, inject, signal } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { HeaderComponent } from './layout/header/header.component';
import { FooterComponent } from './layout/footer/footer.component';
import { ToastComponent } from './core/toast/toast/toast.component';
import { catchError, of, firstValueFrom } from 'rxjs';
import { AuthService } from './core/services/auth.service';
import 'swiper/css';
import { register } from 'swiper/element/bundle';
import { CommonModule } from '@angular/common';

register();

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent, ToastComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})

export class App {
  protected readonly title = signal('matchly-app-frontend');
  private router = inject(Router);
  private authService = inject(AuthService);

  get isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  get isMessagesPage(): boolean {
    return this.router.url.startsWith('/chats');
  }

  get isMatchingPages(): boolean {
    return this.router.url.startsWith('/matches');
  }

  async ngOnInit() {
    await firstValueFrom(
      this.authService.restoreUser().pipe(
        catchError(() => of(null))
      )
    );
  }
}
