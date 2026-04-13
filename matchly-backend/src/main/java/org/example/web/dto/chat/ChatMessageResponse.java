package org.example.web.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ChatMessageResponse {

    private UUID id;
    private UUID chatRoomId;
    private UUID senderId;
    private String senderUsername;
    private String content;
    private LocalDateTime sentAt;
    private boolean isMine;

}
