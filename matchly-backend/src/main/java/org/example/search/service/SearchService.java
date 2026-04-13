package org.example.search.service;

import org.example.exception.NotFoundException;
import org.example.search.model.UserSearchCriteria;
import org.example.search.repository.SearchCriteriaRepository;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.repository.UserRepository;
import org.example.web.dto.search.UserSearchRequest;
import org.example.web.dto.search.UserSearchResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final UserRepository userRepository;
    private final SearchCriteriaRepository searchCriteriaRepository;

    public SearchService(UserRepository userRepository,
                         SearchCriteriaRepository searchCriteriaRepository) {
        this.userRepository = userRepository;
        this.searchCriteriaRepository = searchCriteriaRepository;
    }

    public List<UserSearchResult> searchUsers(AuthenticationDetails authenticationDetails,
                                              UserSearchRequest userSearchRequest) {
        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        UserSearchCriteria criteria = UserSearchCriteria.builder()
                .user(user)
                .username(userSearchRequest.getUsername())
                .town(userSearchRequest.getTown())
                .minAge(userSearchRequest.getMinAge())
                .maxAge(userSearchRequest.getMaxAge())
                .searchedAt(LocalDateTime.now())
                .build();
        searchCriteriaRepository.save(criteria);

        List<User> results = userRepository.findByFilters(
                userSearchRequest.getUsername(),
                userSearchRequest.getTown(),
                userSearchRequest.getMinAge(),
                userSearchRequest.getMaxAge()
        );

        return results.stream()
                .filter(u -> !u.getId().equals(user.getId()))
                .map(u -> UserSearchResult.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .firstName(u.getFirstName())
                        .town(u.getTown())
                        .age(u.getAge())
                        .profilePicture(u.getProfilePicture())
                        .build())
                .collect(Collectors.toList());
    }

}
