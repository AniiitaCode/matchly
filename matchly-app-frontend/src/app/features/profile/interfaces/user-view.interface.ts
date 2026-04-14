import { GenderType } from "../../../shared/enums/gender-type.enum";
import { ChildrenStatus } from "../enums/dating-profile-enums/children-status.enum";
import { HobbyType } from "../enums/dating-profile-enums/hobby-type.enum";
import { RelationshipStatus } from "../enums/dating-profile-enums/relationship-status.enum";
import { RelationshipType } from "../enums/dating-profile-enums/relationship-type.enum";
import { AlcoholConsumption } from "../enums/personality-profile-enums/alcohol-consumption.enum";
import { SmokingHabit } from "../enums/personality-profile-enums/smoking-habit.enum";
import { YesNoPreference } from "../enums/personality-profile-enums/yes-no-preference.enum";
import { ZodiacSign } from "../enums/personality-profile-enums/zodiac-sign.enum";

export interface UserViewDto {

    id: string;
    firstName: string;
    username: string;
    age: number;
    town: string;
    gender: GenderType;
    profilePicture: string;
    createdOn: string;
    updatedOn: string;
    bio: string;
    hobbies: HobbyType[];
    relationshipStatus: RelationshipStatus;
    relationshipType: RelationshipType;
    childrenStatus: ChildrenStatus;
    smokingHabit: SmokingHabit;
    alcoholConsumption: AlcoholConsumption;
    hasPets: YesNoPreference;
    zodiacSign: ZodiacSign;

}