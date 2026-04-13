package org.example.search.repository;

import org.example.search.model.UserSearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SearchCriteriaRepository extends JpaRepository<UserSearchCriteria, UUID> {

}
