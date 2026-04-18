import { ChatStatus } from "../enums/chat.enum";

export interface ChatMessageResponse {
    id: string;
    chatRoomId: string;
    senderId: string;
    senderUsername: string;
    content: string;
    sentAt: string;
    isMine: boolean;
}

export interface ChatMessageRequest {
  chatRoomId: string;
  content: string;
}

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;   
  first: boolean;
  last: boolean;
}

export interface ChatRoomPreview {
  id: string;           
  userName: string;     
  lastMessage: string;  
  lastMessageSentAt?: string; 
  senderId: string;
  senderUsername: string;
  otherUsername: string;
}

export interface ChatRoomResponse {
  id: string;
  userAId: string;
  userBId: string;
  status: ChatStatus;
  createdOn: string;
  lastMessage: string;
  otherUsername: string;
}
