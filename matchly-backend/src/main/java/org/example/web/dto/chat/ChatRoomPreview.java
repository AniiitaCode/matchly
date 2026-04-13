package org.example.web.dto.chat;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatRoomPreview {

    private UUID id;
    private String userName;
    private String lastMessage;

}
