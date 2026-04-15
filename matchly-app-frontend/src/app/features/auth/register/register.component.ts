import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { RegisterCredentials, RegisterResponse } from '../../../core/interfaces/auth.interface';
import { GenderType, GenderDisplayName } from '../../../shared/enums/gender-type.enum';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../core/toast/services/toast.service';

@Component({
  selector: 'app-register',
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})

export class RegisterComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);
  private toastService = inject(ToastService);

  registerForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ]),

    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),

    username: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ]),

    age: new FormControl<number | null>(null, [
      Validators.required,
      Validators.min(18)
    ]),

    town: new FormControl('', [
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(20)
    ]),

    gender: new FormControl<GenderType | null>(null, [
      Validators.required
    ]),

    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ]),

    confirmPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20)
    ])
  });

  GenderType = GenderType;
  GenderDisplayName = GenderDisplayName;

  ngOnInit(): void {
    this.authService.getRegisterPage().subscribe({
      next: (response: RegisterResponse) => {
        const request = response.registerRequest;
        this.registerForm.patchValue({

          firstName: request?.firstName || '',
          email: request?.email || '',
          username: request?.username || '',
          age: request?.age || null,
          town: request?.town || '',
          gender: request?.gender || null,
          password: (''),
          confirmPassword: ('')
        });
      },
      error: (err) => {
        this.toastService.show('Грешка при зареждане на формата', 'error');
      }
    });
  }

  onRegister(): void {
    const credentials: RegisterCredentials = {
      firstName: this.registerForm.get('firstName')?.value || '',
      email: this.registerForm.get('email')?.value || '',
      username: this.registerForm.get('username')?.value || '',
      age: this.registerForm.get('age')?.value || 0,
      town: this.registerForm.get('town')?.value || '',
      gender: this.registerForm.get('gender')?.value!,
      password: this.registerForm.get('password')?.value || '',
      confirmPassword: this.registerForm.get('confirmPassword')?.value || ''
    }


    this.authService.register(credentials)
      .subscribe({
        next: (response: RegisterResponse) => {
          if (response.errors?.length) {
            this.toastService.show(response.errors.join('\n'), 'error');
          } else {
            this.router.navigate([response.redirectTo ?? '/login']);
          }
        },
        error: (err) => {

          if (err.error?.errors) {
            this.toastService.show(err.error.errors.join('\n'), 'error');
          } else {
            this.toastService.show('Грешка при регистрация. Опитайте отново.', 'error');
          }
        }
      })
  }

}
