package org.example.user.model.question;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.model.user.User;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personality_questions")
public class PersonalityQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SmokingHabit smokingHabit;

    @Enumerated(EnumType.STRING)
    private AlcoholConsumption alcoholConsumption;

    @Enumerated(EnumType.STRING)
    private YesNoPreference likesAnimals;

    @Enumerated(EnumType.STRING)
    private YesNoPreference hasPets;

    @Enumerated(EnumType.STRING)
    private YesNoPreference wantsPets;

    @Enumerated(EnumType.STRING)
    private ZodiacSign zodiacSign;

    @Enumerated(EnumType.STRING)
    private CheatingDefinition cheatingDefinition;

    @Enumerated(EnumType.STRING)
    private CheatingForgiveness cheatingForgiveness;

    @Enumerated(EnumType.STRING)
    private ConflictResolutionStyle conflictResolutionStyle;

    @Enumerated(EnumType.STRING)
    private PartnerIndependenceLevel partnerIndependenceLevel;

    @Enumerated(EnumType.STRING)
    private RelationshipSecretsPolicy relationshipSecretsPolicy;

    @Enumerated(EnumType.STRING)
    private RelationshipPriority relationshipPriority;

    @Enumerated(EnumType.STRING)
    private HurtResponseExpectation hurtResponseExpectation;

    @Enumerated(EnumType.STRING)
    private ApologyMethod apologyMethod;

    @Enumerated(EnumType.STRING)
    private RelationshipRoles relationshipRoles;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
