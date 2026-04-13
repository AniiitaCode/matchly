package org.example.user.repository;

import org.example.user.model.question.PersonalityQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalityQuestionsRepository extends JpaRepository<PersonalityQuestions, UUID> {

}
