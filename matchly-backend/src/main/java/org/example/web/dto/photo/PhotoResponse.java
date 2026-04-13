package org.example.web.dto.photo;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.example.user.model.user.User;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PhotoResponse {

    private UUID id;

    @URL(message = "Невалиден формат!")
    private String url;

    @Size(max = 100, message = "Описанието може да бъде до 100 символа!")
    private String description;

    private LocalDateTime uploadedAt;
    private int likes;
    private int comments;
    private User user;

}
