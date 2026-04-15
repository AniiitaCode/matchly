import { GenderType } from "../../../shared/enums/gender-type.enum";

export interface UserEditRequest {
    firstName: string;
    email: string;
    username: string;
    age: number;
    town: string;
    gender: GenderType;
    profilePicture: string;
}

export interface UserEditResponse {
    success?: boolean;
    errors?: string[];
    redirectTo?: string;
}