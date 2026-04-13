package org.example.user.service;

import org.example.exception.NotFoundException;
import org.example.exception.UsernameAlreadyExistException;
import org.example.security.AuthenticationDetails;
import org.example.user.model.dating.DatingProfile;
import org.example.user.model.question.PersonalityQuestions;
import org.example.user.model.user.GenderType;
import org.example.user.model.user.User;
import org.example.user.model.user.UserRole;
import org.example.user.repository.DatingProfileRepository;
import org.example.user.repository.PersonalityQuestionsRepository;
import org.example.user.repository.UserRepository;
import org.example.web.dto.user.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DatingProfileRepository datingProfileRepository;
    private final PersonalityQuestionsRepository personalityQuestionsRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       DatingProfileRepository datingProfileRepository,
                       PersonalityQuestionsRepository personalityQuestionsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.datingProfileRepository = datingProfileRepository;
        this.personalityQuestionsRepository = personalityQuestionsRepository;
    }

    public void register(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository
                .findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail());

        if (optionalUser.isPresent()) {
            throw new UsernameAlreadyExistException("This username or email already exist!");
        }

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .age(registerRequest.getAge())
                .town(registerRequest.getTown())
                .gender(registerRequest.getGender())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdOn(LocalDateTime.now())
                .build();

        if (userRepository.count() == 0) {
            user.setRole(UserRole.ADMIN);
        }  else  {
            user.setRole(UserRole.USER);
        }

        String baseUrl = "http://localhost:8080";
        if (GenderType.MALE.equals(registerRequest.getGender())) {
            user.setProfilePicture(baseUrl + "/images/profile-man.jpg");
        }  else  {
            user.setProfilePicture(baseUrl + "/images/profile-woman.webp");
        }

        userRepository.save(user);
    }

    /*public boolean login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        return passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
    }*/

    public void editUserData(AuthenticationDetails authenticationDetails,
                             UserEditRequest userEditRequest) {

        User user = getById(authenticationDetails.getUserId());
        user.setFirstName(userEditRequest.getFirstName());
        user.setEmail(userEditRequest.getEmail());
        user.setUsername(userEditRequest.getUsername());
        user.setAge(userEditRequest.getAge());
        user.setTown(userEditRequest.getTown());
        user.setGender(userEditRequest.getGender());
        user.setProfilePicture(userEditRequest.getProfilePicture());
        user.setUpdatedOn(LocalDateTime.now());

        userRepository.save(user);
    }

    public void updateUserDatingProfile(AuthenticationDetails authenticationDetails,
                                        UpdateDatingProfileRequest updateDatingProfileRequest) {

        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        DatingProfile datingProfile = user.getDatingProfile();

        if (datingProfile == null) {
            datingProfile = new DatingProfile();
            datingProfile.setUser(user);
            user.setDatingProfile(datingProfile);
        }

        datingProfile.setBio(updateDatingProfileRequest.getBio());

        if (updateDatingProfileRequest.getHobbies().size() <= 7) {
            datingProfile.setHobbies(updateDatingProfileRequest.getHobbies());
        }  else  {
            throw new RuntimeException("Hobbies size must be 7!");
        }

        datingProfile.setRelationshipStatus(updateDatingProfileRequest.getRelationshipStatus());
        datingProfile.setRelationshipType(updateDatingProfileRequest.getRelationshipType());
        datingProfile.setChildrenStatus(updateDatingProfileRequest.getChildrenStatus());
        datingProfile.setWantsChildren(updateDatingProfileRequest.getWantsChildren());

        datingProfileRepository.save(datingProfile);
    }

    public void updatePersonalityQuestions(AuthenticationDetails authenticationDetails,
                                           UpdatePersonalityQuestionsRequest updatePersonalityQuestionsRequest) {

        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        PersonalityQuestions personalityQuestions = user.getPersonalityQuestions();

        if (personalityQuestions == null) {
            personalityQuestions = new PersonalityQuestions();
            personalityQuestions.setUser(user);
            user.setPersonalityQuestions(personalityQuestions);
        }

        personalityQuestions.setSmokingHabit(updatePersonalityQuestionsRequest.getSmokingHabit());
        personalityQuestions.setAlcoholConsumption(updatePersonalityQuestionsRequest.getAlcoholConsumption());
        personalityQuestions.setLikesAnimals(updatePersonalityQuestionsRequest.getLikesAnimals());
        personalityQuestions.setHasPets(updatePersonalityQuestionsRequest.getHasPets());
        personalityQuestions.setWantsPets(updatePersonalityQuestionsRequest.getWantsPets());
        personalityQuestions.setZodiacSign(updatePersonalityQuestionsRequest.getZodiacSign());
        personalityQuestions.setCheatingDefinition(updatePersonalityQuestionsRequest.getCheatingDefinition());
        personalityQuestions.setCheatingForgiveness(updatePersonalityQuestionsRequest.getCheatingForgiveness());
        personalityQuestions.setConflictResolutionStyle(updatePersonalityQuestionsRequest.getConflictResolutionStyle());
        personalityQuestions.setPartnerIndependenceLevel(updatePersonalityQuestionsRequest.getPartnerIndependenceLevel());
        personalityQuestions.setRelationshipSecretsPolicy(updatePersonalityQuestionsRequest.getRelationshipSecretsPolicy());
        personalityQuestions.setRelationshipPriority(updatePersonalityQuestionsRequest.getRelationshipPriority());
        personalityQuestions.setHurtResponseExpectation(updatePersonalityQuestionsRequest.getHurtResponseExpectation());
        personalityQuestions.setApologyMethod(updatePersonalityQuestionsRequest.getApologyMethod());
        personalityQuestions.setRelationshipRoles(updatePersonalityQuestionsRequest.getRelationshipRoles());

        personalityQuestionsRepository.save(personalityQuestions);
    }

    public User getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id [%s] does not exist!"
                        .formatted(userId)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist!"));

        return new AuthenticationDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public int calculateProfileCompletion(AuthenticationDetails authenticationDetails) {
        UUID userId = authenticationDetails.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        DatingProfile datingProfile = user.getDatingProfile();
        PersonalityQuestions personalityQuestions = user.getPersonalityQuestions();

        int totalFields = 26;
        int filled = 0;

        if (user.getFirstName() != null) filled++;
        if (user.getUsername() != null) filled++;
        if (user.getAge() != null) filled++;
        if (user.getTown()  != null) filled++;
        if (user.getGender() != null) filled++;
        if (user.getProfilePicture() != null) filled++;

        if (datingProfile != null) {
            if (datingProfile.getHobbies() != null) filled++;
            if (datingProfile.getRelationshipStatus() != null) filled++;
            if (datingProfile.getRelationshipType() != null) filled++;
            if (datingProfile.getChildrenStatus() != null) filled++;
            if (datingProfile.getWantsChildren() != null) filled++;
        }

        if (personalityQuestions != null) {
            if (personalityQuestions.getSmokingHabit() != null) filled++;
            if (personalityQuestions.getAlcoholConsumption() != null) filled++;
            if (personalityQuestions.getLikesAnimals() != null) filled++;
            if (personalityQuestions.getHasPets() != null) filled++;
            if (personalityQuestions.getWantsPets() != null) filled++;
            if (personalityQuestions.getZodiacSign() != null) filled++;
            if (personalityQuestions.getCheatingDefinition() != null) filled++;
            if (personalityQuestions.getCheatingForgiveness() != null) filled++;
            if (personalityQuestions.getConflictResolutionStyle() != null) filled++;
            if (personalityQuestions.getPartnerIndependenceLevel() != null) filled++;
            if (personalityQuestions.getRelationshipSecretsPolicy() != null) filled++;
            if (personalityQuestions.getRelationshipPriority() != null) filled++;
            if (personalityQuestions.getHurtResponseExpectation() != null) filled++;
            if (personalityQuestions.getApologyMethod() != null) filled++;
            if (personalityQuestions.getRelationshipRoles() != null) filled++;
        }

        return (int)((double) filled / totalFields * 100);
    }

}
