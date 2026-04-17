import { MatchStatus } from "../enums/match-status.enum";

export interface MatchResponse {

    matchId: string;
    userId: string;
    username: string;
    photoUrl: string;
    overallScore: number;
    matchStatus: MatchStatus;
    matchedOn: string;

}