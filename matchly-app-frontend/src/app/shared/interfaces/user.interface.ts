import { GenderType } from "../enums/gender-type.enum";
import { UserRole } from "../enums/user-role.enum";

export interface User {
    id: string;
    firstName: string;
    email: string;
    username: string;
    age: number;
    town: string;
    gender: GenderType;
    role: UserRole;
    profilePicture?: string;
    createdOn?: string;
    updatedOn?: string;
}