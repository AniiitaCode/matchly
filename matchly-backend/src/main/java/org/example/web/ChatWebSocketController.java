package org.example.web;

import jakarta.validation.Valid;
import org.example.chat.model.ChatMessage;
import org.example.chat.service.ChatService;
import org.example.security.AuthenticationDetails;
import org.example.web.dto.chat.ChatMessageRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(ChatService chatService,
                                   SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    /*@MessageMapping("/chat.send")
    public void sendMessage(@Payload @Valid ChatMessageRequest request,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            messagingTemplate.convertAndSendToUser(
                    request.getSenderId().toString(),
                    "/queue/errors",
                    errors
            );
            return;
        }

        ChatMessage message = chatService.sendMessage(request, authenticationDetails);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + message.getChatRoom().getId(),
                message
        );
    }*/

}
