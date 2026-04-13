package org.example.web.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.question.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonalityQuestionsRequest {

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

}
