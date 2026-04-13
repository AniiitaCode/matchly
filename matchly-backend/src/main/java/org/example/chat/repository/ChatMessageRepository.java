package org.example.chat.repository;

import org.example.chat.model.ChatMessage;
import org.example.chat.model.ChatRoom;
import org.example.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    @Query("SELECT m FROM ChatMessage m " +
            "WHERE m.chatRoom.userA = :user OR m.chatRoom.userB = :user " +
            "ORDER BY m.sentAt ASC")
    Page<ChatMessage> findAllMessagesForUser(@Param("user") User user, Pageable pageable);

    Page<ChatMessage> findAllByChatRoom(ChatRoom room, Pageable pageable);

    Optional<ChatMessage> findFirstByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);

}
