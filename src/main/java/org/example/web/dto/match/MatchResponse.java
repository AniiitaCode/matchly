package org.example.web.dto.match;

import lombok.Builder;
import lombok.Data;
import org.example.match.model.MatchStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MatchResponse {

    private UUID matchId;
    private UUID userId;
    private String username;
    private String photoUrl;
    private double overallScore;
    private MatchStatus matchStatus;
    private LocalDateTime matchedOn;

}
