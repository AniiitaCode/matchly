package org.example.web.dto.photo;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PhotoLikeNotification {

    UUID photoId;
    UUID userId;
    String username;

}
