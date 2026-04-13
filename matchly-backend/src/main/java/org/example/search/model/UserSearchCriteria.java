package org.example.search.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_search_criteria")
public class UserSearchCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String town;

    private Integer minAge;

    private Integer maxAge;

    private LocalDateTime searchedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
