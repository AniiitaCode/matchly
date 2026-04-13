package org.example.web;

import org.example.chat.model.ChatMessage;
import org.example.chat.model.ChatRoom;
import org.example.chat.service.ChatService;
import org.example.match.model.Match;
import org.example.match.service.MatchService;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.chat.ChatMessageRequest;
import org.example.web.dto.chat.ChatMessageResponse;
import org.example.web.dto.chat.ChatRoomResponse;
import org.example.web.dto.match.MatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final UserService userService;
    private final MatchService matchService;
    private final ChatService chatService;

    public ChatController(UserService userService,
                          MatchService matchService,
                          ChatService chatService) {
        this.userService = userService;
        this.matchService = matchService;
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatRoomResponse> getChats(@AuthenticationPrincipal  AuthenticationDetails authenticationDetails) {
        UUID userId = authenticationDetails.getUserId();
        return chatService.getChatsForUser(userId);
    }

    @GetMapping("/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getAllMessagesForUser(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").descending());
        Page<ChatMessageResponse> messages = chatService.getAllMessagesForUser(authenticationDetails, pageable);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getMessages(@PathVariable UUID chatRoomId,
                                                         @AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "50") int size) {

        ChatRoom room = chatService.getChatRoomById(chatRoomId, authenticationDetails);
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").descending());
        return ResponseEntity.ok(chatService.getMessages(room, pageable, authenticationDetails));
    }

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChat(@AuthenticationPrincipal AuthenticationDetails authDetails,
                                                       @RequestParam UUID otherUserId) {
        User currentUser = userService.getById(authDetails.getUserId());
        User otherUser = userService.getById(otherUserId);

        MatchResponse matchResponse = matchService.getMutualMatches(authDetails).stream()
                .filter(m -> m.getUserId().equals(otherUserId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No mutual match exists!"));

        UUID matchId = matchResponse.getMatchId();
        Match match = matchService.getMatchById(matchId);

        if (!match.getUserA().equals(currentUser) && !match.getUserB().equals(currentUser)) {
            throw new IllegalStateException("Current user is not part of this match!");
        }

        ChatRoom chatRoom =
                chatService.createChatIfMutualMatch(currentUser, otherUser, match);

        ChatMessage lastMessage =
                chatService.getLastMessageForChat(chatRoom);

        ChatRoomResponse response = ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .userAId(chatRoom.getUserA().getId())
                .userBId(chatRoom.getUserB().getId())
                .status(chatRoom.getStatus())
                .createdOn(chatRoom.getCreatedOn())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{chatRoomId}/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @PathVariable UUID chatRoomId,
            @RequestBody ChatMessageRequest chatMessageRequest,
            @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        chatMessageRequest.setChatRoomId(chatRoomId);

        ChatMessage message = chatService.sendMessage(chatMessageRequest, authenticationDetails);

        ChatMessageResponse response = ChatMessageResponse.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .senderId(message.getSender().getId())
                .senderUsername(message.getSender().getUsername())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}


