export interface SearchRequest {
    username?: string;
    town?: string;
    minAge?: number;
    maxAge?: number;
}

export interface GetSearchPageResponse {
  page: string;                  
  userSearchRequest?: SearchRequest; 
}

export interface UserSearchResult {
  id: string;
  username: string;
  profilePicture: string;
}

export interface SearchUsersResponse {
  results: UserSearchResult[];
}