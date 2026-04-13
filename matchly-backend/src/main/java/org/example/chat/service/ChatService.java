package org.example.chat.service;

import org.example.chat.model.ChatMessage;
import org.example.chat.model.ChatRoom;
import org.example.chat.model.ChatStatus;
import org.example.chat.repository.ChatMessageRepository;
import org.example.chat.repository.ChatRoomRepository;
import org.example.exception.NotFoundException;
import org.example.match.model.Match;
import org.example.match.model.MatchStatus;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.chat.ChatMessageRequest;
import org.example.web.dto.chat.ChatMessageResponse;
import org.example.web.dto.chat.ChatRoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatService(ChatRoomRepository chatRoomRepository,
                       ChatMessageRepository chatMessageRepository,
                       UserService userService) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }

    public ChatRoom createChatIfMutualMatch(User userA, User userB, Match match) {
        if (match.getMatchStatus() != MatchStatus.MUTUAL_MATCH) {
            throw new IllegalStateException("Cannot create chat before mutual match!");
        }

        if (userA == null || userB == null) {
            throw new IllegalArgumentException("Users cannot be null!");
        }

        Optional<ChatRoom> existingChat = chatRoomRepository
                .findByUsers(userA.getId(), userB.getId());


        return existingChat
                .orElseGet(() -> {
                    ChatRoom room = new ChatRoom();
                    room.setUserA(userA);
                    room.setUserB(userB);
                    room.setStatus(ChatStatus.ACTIVE);
                    room.setCreatedOn(LocalDateTime.now());
                    return chatRoomRepository.save(room);
                });
    }

    public ChatMessage sendMessage(ChatMessageRequest chatMessageRequest, AuthenticationDetails authenticationDetails) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequest.getChatRoomId())
                .orElseThrow(() -> new NotFoundException("Chat room not found!"));

        User sender = userService.getById(authenticationDetails.getUserId());

        ChatMessage message = new ChatMessage();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setContent(chatMessageRequest.getContent());
        message.setSentAt(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    public Page<ChatMessageResponse> getMessages(ChatRoom room, Pageable pageable, AuthenticationDetails authenticationDetails) {
        UUID userId = authenticationDetails.getUserId();
        return chatMessageRepository.findAllByChatRoom(room, pageable)
                .map(message -> ChatMessageResponse.builder()
                        .id(message.getId())
                        .chatRoomId(room.getId())
                        .senderId(message.getSender().getId())
                        .senderUsername(message.getSender().getUsername())
                        .content(message.getContent())
                        .sentAt(message.getSentAt())
                        .isMine(message.getSender().getId().equals(userId))
                        .build()
                );
    }

    public ChatRoom getChatRoomById(UUID chatRoomId, AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        ChatRoom room = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException("Chat room not found!"));

        if (!room.getUserA().getId().equals(currentUser.getId()) &&
                !room.getUserB().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("User is not part of this chat!");
        }

        return room;
    }

    public Page<ChatMessageResponse> getAllMessagesForUser(AuthenticationDetails authenticationDetails, Pageable pageable) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        Page<ChatMessage> messages = chatMessageRepository.findAllMessagesForUser(currentUser, pageable);

        return messages.map(m -> ChatMessageResponse.builder()
                .id(m.getId())
                .chatRoomId(m.getChatRoom().getId())
                .senderId(m.getSender().getId())
                .senderUsername(m.getSender().getUsername())
                .content(m.getContent())
                .sentAt(m.getSentAt())
                .isMine(m.getSender().getId().equals(currentUser.getId()))
                .build());
    }

    public ChatMessage getLastMessageForChat(ChatRoom chatRoom) {
        return chatMessageRepository.findFirstByChatRoomOrderBySentAtDesc(chatRoom)
                .orElse(null);
    }

    public List<ChatRoomResponse> getChatsForUser(UUID userId) {
        List<ChatRoom> rooms = chatRoomRepository
                .findAllByUser(userId);

        return rooms.stream().map(room -> {
            String otherUsername = getOtherUser(room, userId);

            return ChatRoomResponse.builder()
                    .id(room.getId())
                    .userAId(room.getUserA().getId())
                    .userBId(room.getUserB().getId())
                    .status(room.getStatus())
                    .createdOn(room.getCreatedOn())
                    .otherUsername(otherUsername)
                    .lastMessage("")
                    .build();
        }).toList();
    }

    private String getOtherUser(ChatRoom room, UUID userId) {
        if (room.getUserA().getId().equals(userId)) {
            return room.getUserB().getUsername();
        }  else  {
            return room.getUserA().getUsername();
        }
    }
}
