import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEditRequest, UserEditResponse } from '../interfaces/edit-profile.interface';
import { ApiService } from '../../../core/services/api.service';

@Injectable({
  providedIn: 'root',
})

export class UserEditService {
  private apiService = inject(ApiService);

  getEditProfile(): Observable<UserEditRequest> {
    return this.apiService.get<UserEditRequest>('profile', { withCredentials: true });
  }

  putEditProfile(data: UserEditRequest): Observable<UserEditResponse> {
    return this.apiService.put<UserEditResponse>('profile', data, {withCredentials: true });
  }
}
