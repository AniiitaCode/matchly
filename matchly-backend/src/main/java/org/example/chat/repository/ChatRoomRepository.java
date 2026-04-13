package org.example.chat.repository;

import org.example.chat.model.ChatRoom;
import org.example.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    Optional<ChatRoom> findByUserAAndUserB(User userA, User userB);

    @Query("SELECT c FROM ChatRoom c WHERE (c.userA.id = :user1 AND c.userB.id = :user2) OR (c.userA.id = :user2 AND c.userB.id = :user1)")
    Optional<ChatRoom> findByUsers(@Param("user1") UUID user1, @Param("user2") UUID user2);

    @Query("SELECT c FROM ChatRoom c " +
            "WHERE c.userA.id = :userId OR c.userB.id = :userId")
    List<ChatRoom> findAllByUser(@Param("userId") UUID userId);

}
