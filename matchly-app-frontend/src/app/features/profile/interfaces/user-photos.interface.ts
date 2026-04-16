import { User } from "../../../shared/interfaces/user.interface";

export interface UserDto {
  id: string;
  username?: string;
}

export interface PhotoRequest {
    id: string;
    url: string;
    description: string;
    uploadedAt: string;
    likes: number;
    comments: number;
    user: UserDto;
}

export interface LikedByUser {
    id: string;
    username: string;
}

export interface PhotoResponse {
    id: string;
    url: string | null;
    description: string;
    uploadedAt: string;
    likes: number;
    comments: number;
    user?: UserDto
    commentsList?: PhotoCommentResponse[];
    likedBy?: PhotoLikeResponse[]; 
}

export interface PhotoCommentRequest {
  content: string;
}

export interface PhotoCommentResponse {
    id: string;
    photoId: string;
    userId: string;
    username: string;
    content: string;
    commentedAt: string;
}

export interface PhotoLikeResponse {
    userId: string,
    username: string,
    profilePicture: string;
}