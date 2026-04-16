import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { UpdateDatingProfileRequest, UpdateDatingProfileResponse } from '../interfaces/edit-dating-profile.interface';

@Injectable({
  providedIn: 'root',
})

export class UpdateDatingService {
  private apiService = inject(ApiService);

  getDatingProfile(): Observable<UpdateDatingProfileRequest> {
    return this.apiService.get<UpdateDatingProfileRequest>('profile/dating', { withCredentials: true});
  }

  putDatingProfile(data: UpdateDatingProfileRequest): Observable<UpdateDatingProfileResponse> {
    return this.apiService.put<UpdateDatingProfileResponse>('profile/dating', data, {withCredentials: true});
  }
}
