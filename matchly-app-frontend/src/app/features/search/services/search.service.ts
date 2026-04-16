import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { GetSearchPageResponse, SearchRequest, SearchUsersResponse } from '../interfaces/search.interface';

@Injectable({
  providedIn: 'root',
})

export class SearchService {
  private apiService = inject(ApiService);

  getSearchPage(): Observable<GetSearchPageResponse> {
    return this.apiService.get<GetSearchPageResponse>('home/search', { withCredentials: true });
  }

  searchUsers(request: SearchRequest): Observable<SearchUsersResponse> {
    return this.apiService.post<SearchUsersResponse>('home/search', request, { withCredentials: true });
  } 
}
