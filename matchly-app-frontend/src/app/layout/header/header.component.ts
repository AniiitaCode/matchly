import { Component, inject } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { Location, CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [RouterLink, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})

export class HeaderComponent {
  authService = inject(AuthService);
  private router = inject(Router);
  private location = inject(Location);

  isLoginOrRegisterPage(): boolean {
    const currentUrl = this.router.url;
    return currentUrl === '/login' || currentUrl === '/register';
  }

  onLogout(): void {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/']);
    })
  }

  onBack(): void {
    this.location.back();
  }

  isIndexPage(): boolean {
  return this.router.url === '/';
}
}
