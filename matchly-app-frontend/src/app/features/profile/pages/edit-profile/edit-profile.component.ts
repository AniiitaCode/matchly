import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserEditService } from '../../services/user-edit.service';
import { GenderType, GenderDisplayName } from '../../../../shared/enums/gender-type.enum';
import { UserEditRequest, UserEditResponse } from '../../interfaces/edit-profile.interface';
import { FormControl, Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-edit-profile',
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css',
})

export class EditProfileComponent implements OnInit {
  private userEditService = inject(UserEditService);
  private router = inject(Router);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  editForm = new FormGroup({
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

    gender: new FormControl<GenderType | null>(null),

    profilePicture: new FormControl('', [
      Validators.required
    ])
  });

  firstName = '';
  email = '';
  username = '';
  age = 0;
  town = '';
  gender: GenderType | null = null;
  profilePicture = '';

  GenderType = GenderType;
  GenderDisplayName = GenderDisplayName;

  ngOnInit() {
    this.userEditService.getEditProfile().subscribe({
      next: (profile: UserEditRequest) => {
        this.editForm.patchValue({

          firstName: profile.firstName,
          email: profile.email,
          username: profile.username,
          age: profile.age,
          town: profile.town,
          gender: profile.gender,
          profilePicture: profile.profilePicture

        });
        this.ref.detectChanges();
      },
      error: (err) => {
        this.toastService.show('Грешка при зареждане на профила!', 'error');
      }
    });
  }

  onEdit() {
    const formValue = this.editForm.value;

    if (formValue.gender === null) {
      this.toastService.show('Моля, изберете пол!', 'error');
      return;
    }

    const data: UserEditRequest = {
      firstName: formValue.firstName || '',
      email: formValue.email || '',
      username: formValue.username || '',
      age: formValue.age ?? 0,
      town: formValue.town || '',
      gender: formValue.gender!,
      profilePicture: formValue.profilePicture || ''
    };

    this.userEditService.putEditProfile(data).subscribe({
      next: (response: UserEditResponse) => {
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
          this.toastService.show('Грешка при обновяването. Опитайте отново.', 'error');
        }
      }
    })
  }
}
