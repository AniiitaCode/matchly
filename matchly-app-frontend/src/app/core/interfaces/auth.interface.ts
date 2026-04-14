import { GenderType } from "../../shared/enums/gender-type.enum";

export interface AuthUser {
    id: string;
    username: string;
    firstName: string;
}

export interface LoginCredentials {
    username: string;
    password: string;
}

export interface LoginResponse {
    success?: boolean;
    redirectTo?: string;
    page?: string;
    loginRequest?: LoginCredentials;
    errors?: string[];
}

export interface RegisterCredentials {
    firstName: string;
    email: string;
    username: string;
    age: number;
    town: string;
    gender: GenderType;
    password: string;
    confirmPassword: string;
}

export interface RegisterResponse {
    redirectTo?: string;
    page?: string;
    registerRequest?: RegisterCredentials;
    success?: boolean;
    errors?: string[];
}
