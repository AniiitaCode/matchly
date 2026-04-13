package org.example.match.service;

import org.example.chat.service.ChatService;
import org.example.match.model.Match;
import org.example.match.model.MatchStatus;
import org.example.match.repository.MatchRepository;
import org.example.security.AuthenticationDetails;
import org.example.user.model.dating.HobbyType;
import org.example.user.model.dating.RelationshipStatus;
import org.example.user.model.question.PersonalityQuestions;
import org.example.user.model.question.ZodiacSign;
import org.example.user.model.user.User;
import org.example.user.repository.UserRepository;
import org.example.user.service.UserService;
import org.example.user.service.ZodiacCompatibilityService;
import org.example.web.dto.match.MatchResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MatchService {

    private static final double PERSONALITY_WEIGHT = 0.75;
    private static final double HOBBIES_WEIGHT = 0.10;
    private static final double ZODIAC_WEIGHT = 0.05;
    private static final double LIFESTYLE_WEIGHT = 0.05;
    private static final double RELATIONSHIP_WEIGHT = 0.03;
    private static final double CHILDREN_WEIGHT = 0.02;

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final ZodiacCompatibilityService zodiacCompatibilityService;
    private final ChatService chatService;
    private final UserService userService;

    public MatchService(MatchRepository matchRepository,
                        UserRepository userRepository,
                        ZodiacCompatibilityService zodiacCompatibilityService,
                        ChatService chatService,
                        UserService userService) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.zodiacCompatibilityService = zodiacCompatibilityService;
        this.chatService = chatService;
        this.userService = userService;
    }

    public void generateMatchesForUser(User user) {
        List<User> allUsers = userRepository.findByGenderNot(user.getGender());

        for (User otherUser : allUsers) {

            if (user.getPersonalityQuestions() == null ||
                    otherUser.getPersonalityQuestions() == null ||
                    user.getDatingProfile() == null ||
                    otherUser.getDatingProfile() == null) {
                continue;
            }

            if (user.getId().equals(otherUser.getId())) continue;

            int maxAgeDifference = 8;
            Integer userAge = user.getAge();
            Integer otherAge = otherUser.getAge();
            if (userAge != null && otherAge != null) {
                int ageDiff = Math.abs(userAge - otherAge);
                if (ageDiff > maxAgeDifference) continue;
            }

            if (user.getDatingProfile().getRelationshipStatus() != null &&
                    otherUser.getDatingProfile().getRelationshipStatus() != null) {

                boolean userInRelationship = user.getDatingProfile().getRelationshipStatus() == RelationshipStatus.IN_RELATIONSHIP
                        || user.getDatingProfile().getRelationshipStatus() == RelationshipStatus.MARRIED;

                boolean otherInRelationship = otherUser.getDatingProfile().getRelationshipStatus() == RelationshipStatus.IN_RELATIONSHIP
                        || otherUser.getDatingProfile().getRelationshipStatus() == RelationshipStatus.MARRIED;

                if (userInRelationship || otherInRelationship) {
                    continue;
                }
            }

            if (user.getDatingProfile().getRelationshipType() != null &&
                    otherUser.getDatingProfile().getRelationshipType() != null &&
                    !user.getDatingProfile().getRelationshipType().equals(otherUser.getDatingProfile().getRelationshipType())) {
                continue;
            }

            if (user.getDatingProfile().getWantsChildren() != null &&
                    otherUser.getDatingProfile().getWantsChildren() != null &&
                    !user.getDatingProfile().getWantsChildren().equals(otherUser.getDatingProfile().getWantsChildren())) {
                continue;
            }

            User first = (user.getId().compareTo(otherUser.getId()) < 0) ? user : otherUser;
            User second = (first == user) ? otherUser : user;

            boolean exists = matchRepository.existsByUserAAndUserB(first, second);
            if (exists) continue;

            double personalityScore = calculatePersonalityMatch(
                    user.getPersonalityQuestions(),
                    otherUser.getPersonalityQuestions()
            );

            double hobbiesScore = calculateHobbiesMatch(
                    user.getDatingProfile().getHobbies(),
                    otherUser.getDatingProfile().getHobbies()
            );

            ZodiacSign userSign = user.getPersonalityQuestions().getZodiacSign();
            ZodiacSign otherUserSign = otherUser.getPersonalityQuestions().getZodiacSign();

            double zodiacBonus = calculateZodiacBonus(userSign, otherUserSign);
            double lifestyleBonus = calculateLifestyleBonus(user, otherUser);
            double relationshipBonus = calculateRelationshipBonus(user, otherUser);
            double childrenBonus = calculateChildrenBonus(user, otherUser);

            double overallScore = personalityScore * PERSONALITY_WEIGHT
                    + hobbiesScore * HOBBIES_WEIGHT
                    + zodiacBonus * ZODIAC_WEIGHT
                    + lifestyleBonus * LIFESTYLE_WEIGHT
                    + relationshipBonus * RELATIONSHIP_WEIGHT
                    + childrenBonus * CHILDREN_WEIGHT;

            if (overallScore < 50) {
                continue;
            }

            Match match = Match.builder()
                    .userA(first)
                    .userB(second)
                    .personalityScore(personalityScore)
                    .hobbiesScore(hobbiesScore)
                    .overallScore(overallScore)
                    .matchedOn(LocalDateTime.now())
                    .matchStatus(MatchStatus.PENDING)
                    .userAApproved(false)
                    .userBApproved(false)
                    .build();

            matchRepository.save(match);
        }
    }

    private double calculatePersonalityMatch(PersonalityQuestions a,
                                             PersonalityQuestions b) {

        if (a == null || b == null) {
            return 0.0;
        }

        double score = 0;
        double totalQuestions = 12;

        if (a.getLikesAnimals() == b.getLikesAnimals()) score++;

        if (a.getHasPets() == b.getHasPets()) score++;

        if (a.getWantsPets() == b.getWantsPets()) score++;

        if (a.getCheatingDefinition() == b.getCheatingDefinition()) score++;

        if (a.getCheatingForgiveness() == b.getCheatingForgiveness()) score++;

        if (a.getConflictResolutionStyle() == b.getConflictResolutionStyle()) score++;

        if (a.getPartnerIndependenceLevel() == b.getPartnerIndependenceLevel()) score++;

        if (a.getRelationshipSecretsPolicy() == b.getRelationshipSecretsPolicy()) score++;

        if (a.getRelationshipPriority() == b.getRelationshipPriority()) score++;

        if (a.getHurtResponseExpectation() == b.getHurtResponseExpectation()) score++;

        if (a.getApologyMethod() == b.getApologyMethod()) score++;

        if (a.getRelationshipRoles() == b.getRelationshipRoles()) score++;

        return (score / totalQuestions) * 100;
    }

    private double calculateHobbiesMatch(List<HobbyType> hobbiesA,
                                         List<HobbyType> hobbiesB) {

        if (hobbiesA == null || hobbiesB == null || hobbiesA.isEmpty() || hobbiesB.isEmpty()) {
            return 0.0;
        }

        Set<HobbyType> intersection = new HashSet<>(hobbiesA);
        intersection.retainAll(hobbiesB);

        return ((double) intersection.size() / Math.max(hobbiesA.size(), hobbiesB.size())) * 100;
    }

    private double calculateZodiacBonus(ZodiacSign userSign, ZodiacSign otherSign) {
        if (userSign == null || otherSign == null) return 0;

        List<ZodiacSign> compatible =
                zodiacCompatibilityService.getCompatibleZodiacs(userSign);

        return compatible.contains(otherSign) ? 5 : 0;
    }

    private double calculateLifestyleBonus(User user, User otherUser) {
        double score = 0.0;
        int maxPoint = 10;

        if (user.getPersonalityQuestions().getAlcoholConsumption() == otherUser.getPersonalityQuestions().getAlcoholConsumption()) {
            score += 5;
        }

        if (user.getPersonalityQuestions().getSmokingHabit() == otherUser.getPersonalityQuestions().getSmokingHabit()) {
            score += 5;
        }

        return (score / maxPoint) * 100;
    }

    private double calculateRelationshipBonus(User user, User otherUser) {
        if (user.getDatingProfile() == null || otherUser.getDatingProfile() == null) return 0;

        int maxPoint = 3;
        int score = (user.getDatingProfile().getRelationshipStatus()
                == otherUser.getDatingProfile().getRelationshipStatus()) ? maxPoint : 0;

        return ((double) score / maxPoint) * 100;
    }

    private double calculateChildrenBonus(User user, User otherUser) {
        if (user.getDatingProfile() == null || otherUser.getDatingProfile() == null) return 0;

        int maxPoint = 2;
        int score = (user.getDatingProfile().getChildrenStatus()
                == otherUser.getDatingProfile().getChildrenStatus()) ? maxPoint : 0;

        return ((double) score / maxPoint) * 100;
    }

    //POST /matches/{matchId}/approve
    public void approveMatch(UUID matchId, AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());
        Match match = matchRepository.findById(matchId)
                .orElseThrow();

        if (match.getMatchStatus().equals(MatchStatus.PENDING)) {
            if (match.getUserA().getId().equals(currentUser.getId())) {
                match.setUserAApproved(true);
            }

            if (match.getUserB().getId().equals(currentUser.getId())) {
                match.setUserBApproved(true);
            }
        }  else if (match.getMatchStatus().equals(MatchStatus.REJECTED)) {
            if (match.getUserA().getId().equals(currentUser.getId())) {
                match.setUserARejected(false);
            }

            if (match.getUserB().getId().equals(currentUser.getId())) {
                match.setUserBRejected(false);
            }
        }

        updateMatchStatus(match);
        matchRepository.save(match);
    }

    private void updateMatchStatus(Match match) {

        if (match.isUserARejected() ||  match.isUserBRejected()) {
            match.setMatchStatus(MatchStatus.REJECTED);
        } else if (match.isUserAApproved() && match.isUserBApproved()) {
            match.setMatchStatus(MatchStatus.MUTUAL_MATCH);
            chatService.createChatIfMutualMatch(match.getUserA(), match.getUserB(), match);
        } else {
            match.setMatchStatus(MatchStatus.PENDING);
        }
    }

    //POST /matches/{matchId}/reject
    public void rejectMatch(UUID matchId, AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalStateException("Match not found!"));

        if (!match.getUserA().getId().equals(currentUser.getId()) &&
                !match.getUserB().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("User is not part of this match!");
        }

        if (match.getUserA().getId().equals(currentUser.getId())) {
            match.setUserARejected(true);
        } else {
            match.setUserBRejected(true);
        }

        if (match.isUserARejected() || match.isUserBRejected()) {
            match.setMatchStatus(MatchStatus.REJECTED);
        }

        matchRepository.save(match);
    }

    public List<MatchResponse> getMatchesForUser(AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        List<Match> matches = matchRepository.findAllByUserAOrUserBOrderByOverallScoreDesc(currentUser, currentUser);
        List<MatchResponse> results = new ArrayList<>();

        for (Match match : matches) {

            boolean isCurrentUserA = match.getUserA().equals(currentUser);
            User otherUser = isCurrentUserA ? match.getUserB() : match.getUserA();

            boolean approved = isCurrentUserA ? match.isUserAApproved() : match.isUserBApproved();
            boolean rejected = isCurrentUserA ? match.isUserARejected() : match.isUserBRejected();
            boolean isPending = !approved && !rejected;

            if (isPending) {
                MatchResponse matchResponse = MatchResponse.builder()
                        .matchId(match.getId())
                        .userId(otherUser.getId())
                        .username(otherUser.getUsername())
                        .photoUrl(otherUser.getProfilePicture())
                        .overallScore(match.getOverallScore())
                        .matchStatus(match.getMatchStatus())
                        .matchedOn(match.getMatchedOn())
                        .build();

                results.add(matchResponse);
            }
        }
        return results;
    }

    public List<MatchResponse> getMutualMatches(AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        List<Match> matches = matchRepository
                .findAllByUserAOrUserBOrderByOverallScoreDesc(currentUser, currentUser);

        List<MatchResponse> results = new ArrayList<>();

        for (Match match : matches) {

            if (match.isUserAApproved() && match.isUserBApproved()) {
                User otherUser = match.getUserA().equals(currentUser)
                        ? match.getUserB() : match.getUserA();

                MatchResponse response = MatchResponse.builder()
                        .matchId(match.getId())
                        .userId(otherUser.getId())
                        .username(otherUser.getUsername())
                        .photoUrl(otherUser.getProfilePicture())
                        .overallScore(match.getOverallScore())
                        .matchedOn(match.getMatchedOn())
                        .build();

                results.add(response);
            }
        }
        return results;
    }

    public List<MatchResponse> getRejectedMatches(AuthenticationDetails authenticationDetails) {
        User currentUser = userService.getById(authenticationDetails.getUserId());

        List<Match> matches = matchRepository
                .findAllByUserAOrUserBOrderByOverallScoreDesc(currentUser, currentUser);

        List<MatchResponse> results = new ArrayList<>();

        for (Match match : matches) {

            boolean isCurrentUserA = match.getUserA().equals(currentUser);
            boolean rejected = isCurrentUserA
                    ? match.isUserARejected()
                    : match.isUserBRejected();

            if (rejected) {
                User otherUser = isCurrentUserA ? match.getUserB() : match.getUserA();

                MatchResponse response = MatchResponse.builder()
                        .matchId(match.getId())
                        .userId(otherUser.getId())
                        .username(otherUser.getUsername())
                        .photoUrl(otherUser.getProfilePicture())
                        .overallScore(match.getOverallScore())
                        .matchStatus(match.getMatchStatus())
                        .matchedOn(match.getMatchedOn())
                        .build();

                results.add(response);
            }
        }
        return results;
    }

    public Match getMatchById(UUID matchId) {
        return matchRepository.findById(matchId).orElseThrow();
    }

    public void deleteMatch(UUID matchId) {
        Match match = getMatchById(matchId);
        match.setMatchStatus(MatchStatus.REMOVED);
        matchRepository.save(match);
    }
}
