package org.example.web;

import org.example.match.model.Match;
import org.example.match.service.MatchService;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.match.MatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final UserService userService;

    public MatchController(MatchService matchService,
                           UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<MatchResponse>> getMatchesForUser(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        List<MatchResponse> matches = matchService.getMatchesForUser(authenticationDetails);
        return ResponseEntity.ok(matches);
    }

    @PostMapping
    public ResponseEntity<Void> generateMatches(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());
        matchService.generateMatchesForUser(currentUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{matchId}/approve")
    public ResponseEntity<Void> approveMatch(@PathVariable UUID matchId,
                                             @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        matchService.approveMatch(matchId, authenticationDetails);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{matchId}/reject")
    public ResponseEntity<Void> rejectMatch(@PathVariable UUID matchId,
                                            @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        matchService.rejectMatch(matchId, authenticationDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mutual")
    public ResponseEntity<List<MatchResponse>> getMutualMatches(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        List<MatchResponse> mutualMatches = matchService.getMutualMatches(authenticationDetails);
        return ResponseEntity.ok(mutualMatches);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<MatchResponse>> getRejectedMatches(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        List<MatchResponse> rejectedMatches = matchService.getRejectedMatches(authenticationDetails);
        return ResponseEntity.ok(rejectedMatches);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable UUID matchId,
                                            @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());
        Match match = matchService.getMatchById(matchId);

        if (!match.getUserA().getId().equals(currentUser.getId()) &&
                !match.getUserB().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        matchService.deleteMatch(match.getId());
        return ResponseEntity.noContent().build();
    }

}
