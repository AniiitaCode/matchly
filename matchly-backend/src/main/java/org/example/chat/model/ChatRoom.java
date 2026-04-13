package org.example.chat.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_room",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_a_id", "user_b_id"}))
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_a_id", nullable = false)
    private User userA;

    @ManyToOne
    @JoinColumn(name = "user_b_id", nullable = false)
    private User userB;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ChatStatus status = ChatStatus.INACTIVE;

    private LocalDateTime createdOn;

    @PrePersist
    @PreUpdate
    private void normalizeUsersOrder() {
        if (userA != null && userB != null && userA.getId().compareTo(userB.getId()) > 0) {
            User temp = userA;
            userA = userB;
            userB = temp;
        }
    }

}
