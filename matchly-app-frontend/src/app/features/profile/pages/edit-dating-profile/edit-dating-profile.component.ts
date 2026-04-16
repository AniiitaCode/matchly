import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HobbyType, HobbyTypeDisplayName } from '../../enums/dating-profile-enums/hobby-type.enum';
import { RelationshipStatus, RelationshipStatusDisplayName } from '../../enums/dating-profile-enums/relationship-status.enum';
import { RelationshipType, RelationshipTypeDisplayName } from '../../enums/dating-profile-enums/relationship-type.enum';
import { ChildrenStatus, ChildrenStatusDisplayName } from '../../enums/dating-profile-enums/children-status.enum';
import { WantsChildren, WantsChildrenDisplayName } from '../../enums/dating-profile-enums/wants-children.enum';
import { UpdateDatingService } from '../../services/user-update-dating.service';
import { UpdateDatingProfileRequest, UpdateDatingProfileResponse } from '../../interfaces/edit-dating-profile.interface';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-edit-dating-profile',
  imports: [FormsModule, CommonModule],
  templateUrl: './edit-dating-profile.component.html',
  styleUrl: './edit-dating-profile.component.css',
})

export class EditDatingProfileComponent implements OnInit {
  private updateDatingService = inject(UpdateDatingService);
  private router = inject(Router);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  bio = '';

  HobbyType = HobbyType;
  HobbyTypeDisplayName = HobbyTypeDisplayName;
  HobbyKeys = Object.values(HobbyType) as HobbyType[];
  hobbies: HobbyType[] = [];

  onHobbyChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const value = input.value as HobbyType;

    if (input.checked) {
      if (this.hobbies.length >= 7) {
        input.checked = false;
        return;
      }

      this.hobbies = [...this.hobbies, value];
    } else {
      this.hobbies = this.hobbies.filter(h => h !== value);
    }
  }

  trackByHobby(index: number, hobby: HobbyType) {
    return hobby;
  }

  rings = Array.from({ length: 40 }, (_, i) => i);

  relationshipStatus!: RelationshipStatus;
  relationshipType!: RelationshipType;
  childrenStatus!: ChildrenStatus;
  wantsChildren!: WantsChildren;

  RelationshipStatus = RelationshipStatus;
  RelationshipStatusDisplayName = RelationshipStatusDisplayName;
  RelationshipStatusValues = Object.values(RelationshipStatus) as RelationshipStatus[];

  RelationshipType = RelationshipType;
  RelationshipTypeDisplayName = RelationshipTypeDisplayName;
  RelationshipTypeValues = Object.values(RelationshipType) as RelationshipType[];

  ChildrenStatus = ChildrenStatus;
  ChildrenStatusDisplayName = ChildrenStatusDisplayName;
  ChildrenStatusValues = Object.values(ChildrenStatus) as ChildrenStatus[];

  WantsChildren = WantsChildren;
  WantsChildrenDisplayName = WantsChildrenDisplayName;
  WantsChildrenValues = Object.values(WantsChildren) as WantsChildren[];


  ngOnInit(): void {
    this.updateDatingService.getDatingProfile().subscribe({
      next: (profile: UpdateDatingProfileRequest) => {


        this.bio = profile.bio ?? '';
        this.hobbies = profile.hobbies ?? [];

        this.relationshipStatus = profile.relationshipStatus;
        this.relationshipType = profile.relationshipType;
        this.childrenStatus = profile.childrenStatus;
        this.wantsChildren = profile.wantsChildren;

        this.ref.detectChanges();
      },
      error: (err) => {
        this.toastService.show('Грешка при зареждане на профила', 'error');
      }
    });
  }

  onUpdateDating() {
    const data: UpdateDatingProfileRequest = {
      bio: this.bio,
      hobbies: this.hobbies,
      relationshipStatus: this.relationshipStatus,
      relationshipType: this.relationshipType,
      childrenStatus: this.childrenStatus,
      wantsChildren: this.wantsChildren,
    };

    this.updateDatingService.putDatingProfile(data).subscribe({
      next: (response: UpdateDatingProfileResponse) => {
        if (response.errors?.length) {
          this.toastService.show(response.errors.join('\n'), 'error');
        } else {
          this.router.navigate(['/home']);
        }
      },
      error: (err) => {

        if (err.error?.errors) {
          this.toastService.show(err.error.errors.join('\n'), 'error');
        } else {
          this.toastService.show('Грешка при обновяването. Опитайте отново.', 'error');
        }
      }
    });
  }

}
