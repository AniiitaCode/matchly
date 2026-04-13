package org.example.photo.repository;

import org.example.photo.model.Photo;
import org.example.photo.model.PhotoLike;
import org.example.user.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoLikeRepository extends JpaRepository<PhotoLike, UUID> {

    boolean existsByPhotoAndLikedUser(Photo photo, User user);

    int countByPhoto(Photo photo);

    List<PhotoLike> findAllByPhoto(Photo photo);
}
