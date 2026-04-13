package org.example.web.dto.chat;

import lombok.Builder;
import lombok.Data;
import org.example.chat.model.ChatStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ChatRoomResponse {

    private UUID id;
    private UUID userAId;
    private UUID userBId;
    private ChatStatus status;
    private LocalDateTime createdOn;
    private String lastMessage;
    private String otherUsername;

}
