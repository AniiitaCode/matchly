import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { LoginCredentials, LoginResponse } from '../../../core/interfaces/auth.interface';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../core/toast/services/toast.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})

export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ]),

    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ])
  });

  private toastService = inject(ToastService);
  private authService = inject(AuthService);
  private router = inject(Router);

  ngOnInit(): void {
    this.authService.getLoginPage().subscribe({
      next: (response: LoginResponse) => {
        const request = response.loginRequest;
        this.loginForm.patchValue({
          username: request?.username || '',
          password: request?.password || ''
        });
      },
      error: (err) => {
        this.toastService.show('Грешка при зареждане на формата!', 'error');
      }
    });
  }

  onLogin(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const credentials: LoginCredentials = {
      username: this.loginForm.value.username!,
      password: this.loginForm.value.password!
    };

    this.authService.login(credentials)
      .subscribe({
        next: (response: LoginResponse) => {
          console.log(response);
          if (response.errors?.length) {
            this.toastService.show(response.errors.join('\n'), 'error');
          } else {
            this.router.navigate([response.redirectTo ?? '/home']);
          }
        },
        error: (err) => {
          if (err.error?.errors) {
            this.toastService.show(err.error.errors.join('\n'), 'error');
          } else {
            this.toastService.show('Грешка при вход. Опитайте отново.', 'error');
          }
        }
      });
  }

}
