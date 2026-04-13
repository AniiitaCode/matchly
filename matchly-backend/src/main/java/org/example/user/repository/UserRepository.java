package org.example.user.repository;

import org.example.user.model.user.GenderType;
import org.example.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    List<User> findByGenderNot(GenderType gender);

    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:town IS NULL OR u.town = :town) AND " +
            "(:minAge IS NULL OR u.age >= :minAge) AND " +
            "(:maxAge IS NULL OR u.age <= :maxAge)")
    List<User> findByFilters(@Param("username") String username,
                             @Param("town") String town,
                             @Param("minAge") Integer minAge,
                             @Param("maxAge") Integer maxAge);

}
