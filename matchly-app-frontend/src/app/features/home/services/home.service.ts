import { Injectable, inject } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { HomePageResponse } from '../interfaces/home.interface';
import { User } from '../../../shared/interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})

export class HomeService {
  private apiService = inject(ApiService);

  getHomePage(): Observable<HomePageResponse> {
    return this.apiService.get<HomePageResponse>('home', { withCredentials: true});
  }
}
