import { HobbyType } from "../enums/dating-profile-enums/hobby-type.enum";
import { RelationshipStatus } from "../enums/dating-profile-enums/relationship-status.enum";
import { RelationshipType} from "../enums/dating-profile-enums/relationship-type.enum";
import { ChildrenStatus } from "../enums/dating-profile-enums/children-status.enum";
import { WantsChildren } from "../enums/dating-profile-enums/wants-children.enum";

export interface UpdateDatingProfileRequest {
    bio: string;
    hobbies: HobbyType[];
    relationshipStatus: RelationshipStatus;
    relationshipType: RelationshipType;
    childrenStatus: ChildrenStatus;
    wantsChildren: WantsChildren;
}

export interface UpdateDatingProfileResponse {
    success?: boolean;
    errors?: string[];
    redirectTo?: string;
}