package org.example.user.repository;

import org.example.user.model.dating.DatingProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DatingProfileRepository extends JpaRepository<DatingProfile, UUID> {

}
