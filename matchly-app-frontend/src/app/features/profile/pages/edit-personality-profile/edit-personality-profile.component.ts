import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UpdatePersonalityService } from '../../services/user-update-personality.service';
import { SmokingHabit, SmokingHabitDisplayName } from '../../enums/personality-profile-enums/smoking-habit.enum';
import { AlcoholConsumption, AlcoholConsumptionDisplayName } from '../../enums/personality-profile-enums/alcohol-consumption.enum';
import { YesNoPreference, YesNoPreferenceDisplayName } from '../../enums/personality-profile-enums/yes-no-preference.enum';
import { ZodiacSign, ZodiacSignDisplayName } from '../../enums/personality-profile-enums/zodiac-sign.enum';
import { CheatingDefinition, CheatingDefinitionDisplayName } from '../../enums/personality-profile-enums/cheating-definition.enum';
import { CheatingForgiveness, CheatingForgivenessDisplayName } from '../../enums/personality-profile-enums/cheating-forgiveness.enum';
import { ConflictResolutionStyle, ConflictResolutionStyleDisplayName } from '../../enums/personality-profile-enums/conflict-resolution-style.enum';
import { PartnerIndependenceLevel, PartnerIndependenceLevelDisplayName } from '../../enums/personality-profile-enums/partner-independence-level.enum';
import { RelationshipSecretsPolicy, RelationshipSecretsPolicyDisplayName } from '../../enums/personality-profile-enums/relationship-secrets-policy.enum';
import { RelationshipPriority, RelationshipPriorityDisplayName } from '../../enums/personality-profile-enums/relationship-priority.enum';
import { HurtResponseExpectation, HurtResponseExpectationDisplayName } from '../../enums/personality-profile-enums/hurt-response-expectation.enum';
import { ApologyMethod, ApologyMethodDisplayName } from '../../enums/personality-profile-enums/apology-method.enum';
import { RelationshipRoles, RelationshipRolesDisplayName } from '../../enums/personality-profile-enums/relationship-roles.enum';
import { UpdatePersonalityQuestionsRequest } from '../../interfaces/edit-personality-profile.interface';
import { UpdateDatingProfileResponse } from '../../interfaces/edit-dating-profile.interface';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-edit-personality-profile',
  imports: [FormsModule],
  templateUrl: './edit-personality-profile.component.html',
  styleUrl: './edit-personality-profile.component.css',
})

export class EditPersonalityProfileComponent implements OnInit {
  private updatePersonalitySercie = inject(UpdatePersonalityService);
  private router = inject(Router);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  rings = Array.from({ length: 46 }, (_, i) => i);

  smokingHabit!: SmokingHabit;
  alcoholConsumption!: AlcoholConsumption;
  likesAnimals!: YesNoPreference;
  hasPets!: YesNoPreference;
  wantsPets!: YesNoPreference;
  zodiacSign!: ZodiacSign;
  cheatingDefinition!: CheatingDefinition;
  cheatingForgiveness!: CheatingForgiveness;
  conflictResolutionStyle!: ConflictResolutionStyle;
  partnerIndependenceLevel!: PartnerIndependenceLevel;
  relationshipSecretsPolicy!: RelationshipSecretsPolicy;
  relationshipPriority!: RelationshipPriority;
  hurtResponseExpectation!: HurtResponseExpectation;
  apologyMethod!: ApologyMethod;
  relationshipRoles!: RelationshipRoles;

  SmokingHabit = SmokingHabit;
  SmokingHabitDisplayName = SmokingHabitDisplayName;
  SmokingHabitValues = Object.values(SmokingHabit) as SmokingHabit[];

  AlcoholConsumption = AlcoholConsumption;
  AlcoholConsumptionDisplayName = AlcoholConsumptionDisplayName;
  AlocolConcumptionValues = Object.values(AlcoholConsumption) as AlcoholConsumption[];

  LikesAnimals = YesNoPreference;
  LikesAnimalsDisplayName = YesNoPreferenceDisplayName;
  LikesAnimalsValues = Object.values(YesNoPreference) as YesNoPreference[];

  HasPets = YesNoPreference;
  HasPetsDisplayName = YesNoPreferenceDisplayName;
  HasPetsValues = Object.values(YesNoPreference) as YesNoPreference[];

  WantsPets = YesNoPreference;
  WantsPetsDisplayName = YesNoPreferenceDisplayName;
  WantsPetsValues = Object.values(YesNoPreference) as YesNoPreference[];

  ZodiacSign = ZodiacSign;
  ZodiacSignDisplayName = ZodiacSignDisplayName;
  ZodiacSignValues = Object.values(ZodiacSign) as ZodiacSign[];

  CheatingDefinition = CheatingDefinition;
  CheatingDefinitionDisplayName = CheatingDefinitionDisplayName;
  CheatingDefinitionValues = Object.values(CheatingDefinition) as CheatingDefinition[];

  CheatingForgiveness = CheatingForgiveness;
  CheatingForgivenessDisplayName = CheatingForgivenessDisplayName;
  CheatingForgivenessValues = Object.values(CheatingForgiveness) as CheatingForgiveness[];

  ConflictResolutionStyle = ConflictResolutionStyle;
  ConflictResolutionStyleDisplayName = ConflictResolutionStyleDisplayName;
  ConflictResolutionStyleValues = Object.values(ConflictResolutionStyle) as ConflictResolutionStyle[];

  PartnerIndependenceLevel = PartnerIndependenceLevel;
  PartnerIndependenceLevelDisplayName = PartnerIndependenceLevelDisplayName;
  PartnerIndependenceLevelValues = Object.values(PartnerIndependenceLevel) as PartnerIndependenceLevel[];

  RelationshipSecretsPolicy = RelationshipSecretsPolicy;
  RelationshipSecretsPolicyDisplayName = RelationshipSecretsPolicyDisplayName;
  RelationshipSecretsPolicyValues = Object.values(RelationshipSecretsPolicy) as RelationshipSecretsPolicy[];

  RelationshipPriority = RelationshipPriority;
  RelationshipPriorityDisplayName = RelationshipPriorityDisplayName;
  RelationshipPriorityValues = Object.values(RelationshipPriority) as RelationshipPriority[];

  HurtResponseExpectation = HurtResponseExpectation;
  HurtResponseExpectationDisplayName = HurtResponseExpectationDisplayName;
  HurtResponseExpectationValues = Object.values(HurtResponseExpectation) as HurtResponseExpectation[];

  ApologyMethod = ApologyMethod;
  ApologyMethodDisplayName = ApologyMethodDisplayName;
  ApologyMethodValues = Object.values(ApologyMethod) as ApologyMethod[];

  RelationshipRoles = RelationshipRoles;
  RelationshipRolesDisplayName = RelationshipRolesDisplayName;
  RelationshipRolesValues = Object.values(RelationshipRoles) as RelationshipRoles[];

  ngOnInit(): void {
    this.updatePersonalitySercie.getPersonalityProfile().subscribe({
      next: (profile: UpdatePersonalityQuestionsRequest) => {

        this.smokingHabit = profile.smokingHabit;
        this.alcoholConsumption = profile.alcoholConsumption;
        this.likesAnimals = profile.likesAnimals;
        this.hasPets = profile.hasPets;
        this.wantsPets = profile.wantsPets;
        this.zodiacSign = profile.zodiacSign;
        this.cheatingDefinition = profile.cheatingDefinition;
        this.cheatingForgiveness = profile.cheatingForgiveness;
        this.conflictResolutionStyle = profile.conflictResolutionStyle;
        this.partnerIndependenceLevel = profile.partnerIndependenceLevel;
        this.relationshipSecretsPolicy = profile.relationshipSecretsPolicy;
        this.relationshipPriority = profile.relationshipPriority;
        this.hurtResponseExpectation = profile.hurtResponseExpectation;
        this.apologyMethod = profile.apologyMethod;
        this.relationshipRoles = profile.relationshipRoles;

        this.ref.detectChanges();
      },
      error: (err) => {
        this.toastService.show('Грешка при зареждане на профила!', 'error');
      }
    });
  }

  onUpdatePersonality() {
    const data: UpdatePersonalityQuestionsRequest = {
      smokingHabit: this.smokingHabit,
      alcoholConsumption: this.alcoholConsumption,
      likesAnimals: this.likesAnimals,
      hasPets: this.hasPets,
      wantsPets: this.wantsPets,
      zodiacSign: this.zodiacSign,
      cheatingDefinition: this.cheatingDefinition,
      cheatingForgiveness: this.cheatingForgiveness,
      conflictResolutionStyle: this.conflictResolutionStyle,
      partnerIndependenceLevel: this.partnerIndependenceLevel,
      relationshipSecretsPolicy: this.relationshipSecretsPolicy,
      relationshipPriority: this.relationshipPriority,
      hurtResponseExpectation: this.hurtResponseExpectation,
      apologyMethod: this.apologyMethod,
      relationshipRoles: this.relationshipRoles,
    };

    this.updatePersonalitySercie.putPersonalityProfile(data).subscribe({
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
