import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { User } from '../../../shared/interfaces/user.interface';
import { UserViewDto } from '../interfaces/user-view.interface';

@Injectable({
  providedIn: 'root',
})

export class UserService {
  private apiService = inject(ApiService);

  getUserById(userId: string): Observable<UserViewDto> {
    return this.apiService.get<UserViewDto>(`user/${userId}`, { withCredentials: true });
  }
}
