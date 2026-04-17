import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { UserViewDto } from '../../interfaces/user-view.interface';
import { YesNoPreferenceDisplayName } from '../../enums/personality-profile-enums/yes-no-preference.enum';
import { AlcoholConsumptionDisplayName } from '../../enums/personality-profile-enums/alcohol-consumption.enum';
import { SmokingHabitDisplayName } from '../../enums/personality-profile-enums/smoking-habit.enum';
import { ZodiacDisplayName } from '../../../home/enums/zodiac-sign.enum';
import { ChildrenStatusDisplayName } from '../../enums/dating-profile-enums/children-status.enum';
import { RelationshipStatusDisplayName } from '../../enums/dating-profile-enums/relationship-status.enum';
import { UserPhotosComponent } from '../user-photos/user-photos.component';
import { AuthService } from '../../../../core/services/auth.service';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize.pipe';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-user-detail',
  imports: [CommonModule, UserPhotosComponent, CapitalizePipe],
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css',
})

export class UserDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private userService = inject(UserService);
  private ref = inject(ChangeDetectorRef);
  private authService = inject(AuthService);
  private toastService = inject(ToastService);

  isLoggedIn = this.authService.isLoggedIn;

  YesNoPreferenceDisplayName = YesNoPreferenceDisplayName;
  AlcoholConsumptionDisplayName = AlcoholConsumptionDisplayName;
  SmokingHabitDisplayName = SmokingHabitDisplayName;
  ZodiacDisplayName = ZodiacDisplayName;
  ChildrenStatusDisplayName = ChildrenStatusDisplayName;
  RelationshipStatusDisplayName = RelationshipStatusDisplayName;

  rings = Array.from({ length: 34 }, (_, i) => i);

  userId: string | null = null;
  user: UserViewDto | null = null;

  currentUserId: string = '';
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(currentUser => {
      this.currentUserId = currentUser?.id ?? '';


      this.userId = this.route.snapshot.paramMap.get('id');
      if (this.userId) {
        this.userService.getUserById(this.userId).subscribe({
          next: (userDto) => {
            this.user = userDto;
            this.ref.detectChanges();
          },
          error: () => {
            this.toastService.show('Потребителят не беше намерен!', 'error');
          }
        })
      }
    });
  }
}

