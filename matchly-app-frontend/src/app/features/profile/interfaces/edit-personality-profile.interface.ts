import { AlcoholConsumption } from "../enums/personality-profile-enums/alcohol-consumption.enum";
import { ApologyMethod } from "../enums/personality-profile-enums/apology-method.enum";
import { CheatingDefinition } from "../enums/personality-profile-enums/cheating-definition.enum";
import { CheatingForgiveness } from "../enums/personality-profile-enums/cheating-forgiveness.enum";
import { ConflictResolutionStyle } from "../enums/personality-profile-enums/conflict-resolution-style.enum";
import { HurtResponseExpectation } from "../enums/personality-profile-enums/hurt-response-expectation.enum";
import { PartnerIndependenceLevel } from "../enums/personality-profile-enums/partner-independence-level.enum";
import { RelationshipPriority } from "../enums/personality-profile-enums/relationship-priority.enum";
import { RelationshipRoles } from "../enums/personality-profile-enums/relationship-roles.enum";
import { RelationshipSecretsPolicy } from "../enums/personality-profile-enums/relationship-secrets-policy.enum";
import { SmokingHabit } from "../enums/personality-profile-enums/smoking-habit.enum";
import { YesNoPreference } from "../enums/personality-profile-enums/yes-no-preference.enum";
import { ZodiacSign } from "../enums/personality-profile-enums/zodiac-sign.enum";

export interface UpdatePersonalityQuestionsRequest {
    smokingHabit: SmokingHabit,
    alcoholConsumption: AlcoholConsumption,
    likesAnimals: YesNoPreference,
    hasPets: YesNoPreference,
    wantsPets: YesNoPreference,
    zodiacSign: ZodiacSign,
    cheatingDefinition: CheatingDefinition,
    cheatingForgiveness: CheatingForgiveness,
    conflictResolutionStyle: ConflictResolutionStyle,
    partnerIndependenceLevel: PartnerIndependenceLevel,
    relationshipSecretsPolicy: RelationshipSecretsPolicy,
    relationshipPriority: RelationshipPriority,
    hurtResponseExpectation: HurtResponseExpectation,
    apologyMethod: ApologyMethod,
    relationshipRoles: RelationshipRoles;
}

export interface UpdatePersonalityQuestionsResponse {
    success?: boolean,
    errors?: string[],
    redirectTo?: string;
}