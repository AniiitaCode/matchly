import { Injectable, inject } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LandingService {
  private apiService = inject(ApiService);

  checkRedirect(): Observable<{ redirectTo?: string}> {
    return this.apiService.get<{ redirectTo?: string}>('');
  }
  
}
