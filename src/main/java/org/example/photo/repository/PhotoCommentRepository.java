package org.example.photo.repository;

import org.example.photo.model.Photo;
import org.example.photo.model.PhotoComment;
import org.example.photo.model.PhotoLike;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhotoCommentRepository extends JpaRepository<PhotoComment, UUID> {

    List<PhotoComment> findAllByPhotoOrderByCommentedAtAsc(Photo photo);

}
