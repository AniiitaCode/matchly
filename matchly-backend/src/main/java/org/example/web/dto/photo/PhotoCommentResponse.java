package org.example.web.dto.photo;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PhotoCommentResponse {

    private UUID id;
    private UUID photoId;
    private UUID userId;
    private String username;

    @Size(max = 150, message = "Коментарът не може да бъде по-дълъг от 150 символа!")
    private String content;

    private LocalDateTime commentedAt;

}
