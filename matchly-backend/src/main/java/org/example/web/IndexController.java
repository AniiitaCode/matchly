package org.example.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.user.LoginRequest;
import org.example.web.dto.user.RegisterRequest;
import org.example.web.dto.user.UserViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class IndexController {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> index(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        Map<String, Object> response = new HashMap<>();

        if (authenticationDetails != null) {
            response.put("redirectTo", "/home");
        } else {
            response.put("page", "index");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        Map<String, Object> response = new HashMap<>();

        if (authenticationDetails != null) {
            response.put("redirectTo", "/home");
        } else {
            response.put("page", "register");
            response.put("registerRequest", new RegisterRequest());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest registerRequest,
                                                        BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .toList());
            return ResponseEntity.badRequest().body(response);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            response.put("success", false);
            response.put("errors", List.of("Паролите не съвпадат!"));
            return ResponseEntity.badRequest().body(response);
        }

        userService.register(registerRequest);

        response.put("success", true);
        response.put("redirectTo", "/login");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "error", required = false) String errorParam,
                                                     @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        Map<String, Object> response = new HashMap<>();

        if (authenticationDetails != null) {
            response.put("redirectTo", "/home");
        } else {
            response.put("page", "login");
            response.put("loginRequest", new LoginRequest());
        }

        if (errorParam != null) {
            response.put("errors", List.of("Невалидно потребителско име или парола!"));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            response.put("success", false);
            response.put("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .toList());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            response.put("success", true);
            response.put("redirectTo", "/home");

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {

            response.put("success", false);
            response.put("errors", List.of("Невалидно потребителско име или парола!"));

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserViewDto> getCurrentUser(@AuthenticationPrincipal AuthenticationDetails auth) {
        User user = userService.getById(auth.getUserId());
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

}
