package org.example.photo.repository;

import org.example.photo.model.Photo;
import org.example.user.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {

    List<Photo> findAllByUser(User user);

    Optional<Photo> findByUrl(String filename);

    List<Photo> findByUser(User user, Pageable pageable);
}
