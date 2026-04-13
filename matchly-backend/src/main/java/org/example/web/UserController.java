package org.example.web;

import jakarta.validation.Valid;
import org.example.security.AuthenticationDetails;
import org.example.user.model.dating.DatingProfile;
import org.example.user.model.question.PersonalityQuestions;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.user.UpdateDatingProfileRequest;
import org.example.web.dto.user.UpdatePersonalityQuestionsRequest;
import org.example.web.dto.user.UserEditRequest;
import org.example.web.dto.user.UserViewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserViewDto> getUserById(@PathVariable UUID id) {
        User user = userService.getById(id);
        UserViewDto userViewDto = UserViewDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .username(user.getUsername())
                .age(user.getAge())
                .town(user.getTown())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .bio(user.getDatingProfile() != null ? user.getDatingProfile().getBio() : null)
                .hobbies(user.getDatingProfile() != null ? user.getDatingProfile().getHobbies() : null)
                .relationshipStatus(user.getDatingProfile() != null ? user.getDatingProfile().getRelationshipStatus() : null)
                .relationshipType(user.getDatingProfile() != null ? user.getDatingProfile().getRelationshipType() : null)
                .childrenStatus(user.getDatingProfile() != null ? user.getDatingProfile().getChildrenStatus() : null)
                .smokingHabit(user.getPersonalityQuestions() != null ? user.getPersonalityQuestions().getSmokingHabit() : null)
                .alcoholConsumption(user.getPersonalityQuestions() != null ? user.getPersonalityQuestions().getAlcoholConsumption() : null)
                .hasPets(user.getPersonalityQuestions() != null ? user.getPersonalityQuestions().getHasPets() : null)
                .zodiacSign(user.getPersonalityQuestions() != null ? user.getPersonalityQuestions().getZodiacSign() : null)
                .build();

        return ResponseEntity.ok(userViewDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEditRequest> getProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        User user = userService.getById(authenticationDetails.getUserId());

        UserEditRequest userEditRequest = UserEditRequest.builder()
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .username(user.getUsername())
                .age(user.getAge())
                .town(user.getTown())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .build();

        return ResponseEntity.ok(userEditRequest);
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                             @Valid @RequestBody UserEditRequest userEditRequest,
                                                             BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", validationErrors(bindingResult));

            return ResponseEntity.badRequest().body(response);
        }

        userService.editUserData(authenticationDetails, userEditRequest);

        response.put("success", true);
        response.put("redirectTo", "/home");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/dating")
    public ResponseEntity<UpdateDatingProfileRequest> getDatingProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        User user = userService.getById(authenticationDetails.getUserId());
        DatingProfile datingProfile = user.getDatingProfile();

        if (datingProfile == null) {
            return ResponseEntity.ok(new UpdateDatingProfileRequest());
        }

        UpdateDatingProfileRequest updateDatingProfileRequest = UpdateDatingProfileRequest.builder()
                .bio(datingProfile.getBio())
                .hobbies(datingProfile.getHobbies())
                .relationshipStatus(datingProfile.getRelationshipStatus())
                .relationshipType(datingProfile.getRelationshipType())
                .childrenStatus(datingProfile.getChildrenStatus())
                .wantsChildren(datingProfile.getWantsChildren())
                .build();

        return ResponseEntity.ok(updateDatingProfileRequest);
    }

    @PutMapping("/profile/dating")
    public ResponseEntity<Map<String, Object>> updateDatingProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                                   @Valid @RequestBody UpdateDatingProfileRequest updateDatingProfileRequest,
                                                                   BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", validationErrors(bindingResult));

            return ResponseEntity.badRequest().body(response);
        }

        userService.updateUserDatingProfile(authenticationDetails, updateDatingProfileRequest);

        response.put("success", true);
        response.put("redirectTo", "/home");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/personality")
    public ResponseEntity<UpdatePersonalityQuestionsRequest> getPersonalityProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        User user = userService.getById(authenticationDetails.getUserId());
        PersonalityQuestions personalityQuestions = user.getPersonalityQuestions();

        if (personalityQuestions == null) {
            return ResponseEntity.ok(new UpdatePersonalityQuestionsRequest());
        }

        UpdatePersonalityQuestionsRequest updatePersonalityQuestionsRequest = UpdatePersonalityQuestionsRequest.builder()
                .smokingHabit(personalityQuestions.getSmokingHabit())
                .alcoholConsumption(personalityQuestions.getAlcoholConsumption())
                .likesAnimals(personalityQuestions.getLikesAnimals())
                .hasPets(personalityQuestions.getHasPets())
                .wantsPets(personalityQuestions.getWantsPets())
                .zodiacSign(personalityQuestions.getZodiacSign())
                .cheatingDefinition(personalityQuestions.getCheatingDefinition())
                .cheatingForgiveness(personalityQuestions.getCheatingForgiveness())
                .conflictResolutionStyle(personalityQuestions.getConflictResolutionStyle())
                .partnerIndependenceLevel(personalityQuestions.getPartnerIndependenceLevel())
                .relationshipSecretsPolicy(personalityQuestions.getRelationshipSecretsPolicy())
                .relationshipPriority(personalityQuestions.getRelationshipPriority())
                .hurtResponseExpectation(personalityQuestions.getHurtResponseExpectation())
                .apologyMethod(personalityQuestions.getApologyMethod())
                .relationshipRoles(personalityQuestions.getRelationshipRoles())
                .build();

        return ResponseEntity.ok(updatePersonalityQuestionsRequest);
    }

    @PutMapping("/profile/personality")
    public ResponseEntity<Map<String, Object>> updatePersonalityProfile(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                                        @Valid @RequestBody UpdatePersonalityQuestionsRequest updatePersonalityQuestionsRequest,
                                                                        BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", validationErrors(bindingResult));

            return ResponseEntity.badRequest().body(response);
        }

        userService.updatePersonalityQuestions(authenticationDetails, updatePersonalityQuestionsRequest);

        response.put("success", true);
        response.put("redirectTo", "/home");
        return ResponseEntity.ok(response);
    }

    private Map<String, String> validationErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
    }

}
