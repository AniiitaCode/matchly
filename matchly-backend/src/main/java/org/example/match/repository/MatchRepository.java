package org.example.match.repository;

import org.example.match.model.Match;
import org.example.match.model.MatchStatus;
import org.example.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {

    List<Match> findAllByUserAOrUserBAndMatchStatus(User userA, User userB, MatchStatus matchStatus);

    boolean existsByUserAAndUserB(User first, User second);

    List<Match> findAllByUserAOrUserBOrderByOverallScoreDesc(User user, User user1);

}
