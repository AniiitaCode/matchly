package org.example.web.dto.home;

import lombok.Builder;
import lombok.Data;
import org.example.user.model.question.ZodiacSign;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class HomePageResponse {

    private List<ZodiacSign> compatibleZodiacs;
    private int profileCompletion;
    private UUID userId;

}
