package org.example.user.model.dating;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.model.user.User;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dating_profile")
public class DatingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String bio;

    @Enumerated(EnumType.STRING)
    private List<HobbyType> hobbies;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;

    @Enumerated(EnumType.STRING)
    private ChildrenStatus childrenStatus;

    @Enumerated(EnumType.STRING)
    private WantsChildren wantsChildren;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
