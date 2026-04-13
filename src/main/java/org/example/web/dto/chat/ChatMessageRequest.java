package org.example.web.dto.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessageRequest {

    private UUID chatRoomId;

    @NotBlank(message = "Съобщението не може да бъде празно!")
    private String content;

}
