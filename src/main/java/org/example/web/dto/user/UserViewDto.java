package org.example.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.dating.ChildrenStatus;
import org.example.user.model.dating.HobbyType;
import org.example.user.model.dating.RelationshipStatus;
import org.example.user.model.dating.RelationshipType;
import org.example.user.model.question.AlcoholConsumption;
import org.example.user.model.question.SmokingHabit;
import org.example.user.model.question.YesNoPreference;
import org.example.user.model.question.ZodiacSign;
import org.example.user.model.user.GenderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDto {

    private UUID id;
    private String firstName;
    private String username;
    private Integer age;
    private String town;
    private GenderType gender;
    private String profilePicture;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String bio;
    private List<HobbyType> hobbies;
    private RelationshipStatus relationshipStatus;
    private RelationshipType relationshipType;
    private ChildrenStatus childrenStatus;
    private SmokingHabit smokingHabit;
    private AlcoholConsumption alcoholConsumption;
    private YesNoPreference hasPets;
    private ZodiacSign zodiacSign;

}
