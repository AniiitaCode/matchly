import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { PhotoResponse, PhotoCommentResponse, PhotoLikeResponse } from '../interfaces/user-photos.interface';

@Injectable({
  providedIn: 'root',
})

export class UserPhotosService {
  private apiService = inject(ApiService);

  getPhotosByUser(userId: string, limit: number = 9, offset: number = 0): Observable<PhotoResponse[]> {
    let url = `photos/${userId}`; 
    const query: string[] = [];

    if (limit != null) query.push(`limit=${limit}`);
    if (offset != null) query.push(`offset=${offset}`);

    if (query.length) url += '?' + query.join('&');

    return this.apiService.get<PhotoResponse[]>(url, { withCredentials: true });
  }

  postPhoto(data: FormData): Observable<PhotoResponse> {
    return this.apiService.post<PhotoResponse>('photos', data, { withCredentials: true });
  }

  getPhotoFile(filename: string): Observable<Blob> {
    return this.apiService.getBlob(`http://localhost:8080/api/photos/file/${filename}`, { withCredentials: true });
  }

  postLikePhoto(photoId: string) {
    return this.apiService.post<void>(`photos/${photoId}/likes`, {}, { withCredentials: true });
  }

  getPhotoLikes(photoId: string): Observable<PhotoLikeResponse[]> {
    return this.apiService.get<PhotoLikeResponse[]>(`photos/${photoId}/likes`, { withCredentials: true });
  }

  getPhotoComments(photoId: string): Observable<PhotoCommentResponse[]> {
    return this.apiService.get<PhotoCommentResponse[]>(`photos/${photoId}/comments`, { withCredentials: true });
  }

  postCommentPhoto(photoId: string, content: string): Observable<PhotoCommentResponse> {
    const body = { content };
    return this.apiService.post<PhotoCommentResponse>(`photos/${photoId}/comments`, body, { withCredentials: true });
  }

  deletePhoto(photoId: string): Observable<void> {
    return this.apiService.delete(`photos/${photoId}`, { withCredentials: true });
  }

}
