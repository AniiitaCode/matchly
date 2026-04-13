package org.example.web.dto.search;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserSearchResult {

    private UUID id;
    private String username;
    private String firstName;
    private String town;
    private Integer age;
    private String profilePicture;

}
