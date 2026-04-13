package org.example.web;

import org.example.search.service.SearchService;
import org.example.security.AuthenticationDetails;
import org.example.user.model.question.PersonalityQuestions;
import org.example.user.model.question.ZodiacSign;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.user.service.ZodiacCompatibilityService;
import org.example.web.dto.home.HomePageResponse;
import org.example.web.dto.search.UserSearchRequest;
import org.example.web.dto.search.UserSearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final UserService userService;
    private final SearchService searchService;
    private final ZodiacCompatibilityService zodiacCompatibilityService;

    public HomeController(UserService userService,
                          SearchService searchService,
                          ZodiacCompatibilityService zodiacCompatibilityService) {
        this.userService = userService;
        this.searchService = searchService;
        this.zodiacCompatibilityService = zodiacCompatibilityService;
    }

    @GetMapping
    public ResponseEntity<HomePageResponse> getHomePage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        if (authenticationDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.getById(authenticationDetails.getUserId());
        ZodiacSign userSign = Optional.ofNullable(user.getPersonalityQuestions())
                .map(PersonalityQuestions::getZodiacSign)
                .orElse(null);
        List<ZodiacSign> compatible = zodiacCompatibilityService.getCompatibleZodiacs(userSign);

        int profileCompletion = userService.calculateProfileCompletion(authenticationDetails);

        HomePageResponse response = HomePageResponse.builder()
                .compatibleZodiacs(compatible)
                .profileCompletion(profileCompletion)
                .userId(user.getId())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getSearchPage() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", "search");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserSearchResult>> searchUsers(
            @AuthenticationPrincipal AuthenticationDetails authenticationDetails,
            @RequestBody UserSearchRequest userSearchRequest) {

        List<UserSearchResult> results = searchService.searchUsers(authenticationDetails, userSearchRequest);
        return ResponseEntity.ok(results);
    }

}
