package org.example.web.dto.photo;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PhotoLikeResponse {

    private UUID userId;
    private String username;
    private String profilePicture;

}
