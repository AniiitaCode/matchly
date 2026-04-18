import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Observable } from 'rxjs';
import { ChatMessageRequest, ChatMessageResponse, ChatRoomResponse } from '../interfaces/chat.interface';
import { Page } from '../interfaces/chat.interface';

@Injectable({
  providedIn: 'root',
})

export class ChatService {
  private apiService = inject(ApiService);

  getChats(): Observable<ChatRoomResponse[]> {
    return this.apiService.get<ChatRoomResponse[]>(
      'chats', { withCredentials: true });
  }

  getAllMessages(page: number = 0, size: number = 50): Observable<Page<ChatMessageResponse>> {
    return this.apiService.get<Page<ChatMessageResponse>>
      (`chats/messages?page=${page}&size=${size}`, { withCredentials: true });
  }

  getSelectedMessages(chatRoomId: string, page: number = 0, size: number = 50): Observable<Page<ChatMessageResponse>> {
    return this.apiService.get<Page<ChatMessageResponse>>
      (`chats/${chatRoomId}/messages?page=${page}&size=${size}`, { withCredentials: true });
  }

  createChat(otherUserId: string): Observable<ChatRoomResponse> {
    return this.apiService.post<ChatRoomResponse>(`chats?otherUserId=${otherUserId}`, {}, { withCredentials: true });
  }

  sendMessage(chatRoomId: string, content: string): Observable<ChatMessageResponse> {
    const body: ChatMessageRequest = {
      chatRoomId,
      content
    }

    return this.apiService.post<ChatMessageResponse>(`chats/${chatRoomId}/messages`, body, { withCredentials: true });
  }
}



