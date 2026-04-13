package org.example.match.model;

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
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "personality_score", nullable = false)
    private double personalityScore;

    @Column(name = "hobbies_score", nullable = false)
    private double hobbiesScore;

    @Column(name = "overall_score", nullable = false)
    private double overallScore;

    private LocalDateTime matchedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id", nullable = false)
    private User userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id", nullable = false)
    private User userB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus matchStatus;

    @Column(nullable = false)
    private boolean userAApproved;

    @Column(nullable = false)
    private boolean userBApproved;

    @Column(nullable = false)
    private boolean userARejected;

    @Column(nullable = false)
    private boolean userBRejected;

}
