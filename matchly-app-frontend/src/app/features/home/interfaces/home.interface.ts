import { ZodiacSign } from "../enums/zodiac-sign.enum";

export interface HomePageResponse {
    compatibleZodiacs: ZodiacSign[];
    profileCompletion: number;
    userId: string;
    
}