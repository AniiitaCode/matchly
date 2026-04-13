package org.example.web.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.model.dating.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDatingProfileRequest {

    @Size(max = 100, message = "Биографията не може да бъде повече от 50 символа!")
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

}
