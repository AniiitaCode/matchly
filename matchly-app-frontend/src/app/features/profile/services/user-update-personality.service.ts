import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '../../../core/services/api.service';
import { UpdateDatingProfileRequest, UpdateDatingProfileResponse } from '../interfaces/edit-dating-profile.interface';
import { UpdatePersonalityQuestionsRequest, UpdatePersonalityQuestionsResponse } from '../interfaces/edit-personality-profile.interface';

@Injectable({
  providedIn: 'root',
})

export class UpdatePersonalityService {
    private apiService = inject(ApiService);

    getPersonalityProfile(): Observable<UpdatePersonalityQuestionsRequest> {
        return this.apiService.get<UpdatePersonalityQuestionsRequest>('profile/personality', { withCredentials: true });
    }

    putPersonalityProfile(data: UpdatePersonalityQuestionsRequest): Observable<UpdatePersonalityQuestionsResponse> {
        return this.apiService.put<UpdatePersonalityQuestionsResponse>('profile/personality', data, { withCredentials: true });
    }
}