import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { MatchResponse } from '../interfaces/match.interface';

@Injectable({
  providedIn: 'root',
})
export class MatchService {
  private apiService = inject(ApiService);

  getMatchesPage(): Observable<MatchResponse[]> {
    return this.apiService.get<MatchResponse[]>('matches', { withCredentials: true });
  }

  getMutualMatches(): Observable<MatchResponse[]> {
    return this.apiService.get<MatchResponse[]>('matches/mutual', { withCredentials: true });
  }

  getRejectedMatches(): Observable<MatchResponse[]> {
    return this.apiService.get<MatchResponse[]>('matches/rejected', { withCredentials: true });
  }

  postGenerateMatches(): Observable<void> {
    return this.apiService.post<void>('matches', null, { withCredentials: true });
  }

  approveMatch(matchId: string): Observable<void> {
    return this.apiService.post<void>(`matches/${matchId}/approve`, null, { withCredentials: true });
  } 

  rejectMatch(matchId: string): Observable<void> {
    return this.apiService.post<void>(`matches/${matchId}/reject`, null, { withCredentials: true });
  }

  deleteMatch(matchId: string): Observable<void> {
    return this.apiService.delete<void>(`matches/${matchId}`, { withCredentials: true });
  }

}
