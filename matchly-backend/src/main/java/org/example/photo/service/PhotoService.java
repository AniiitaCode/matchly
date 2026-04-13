package org.example.photo.service;

import org.example.exception.NotFoundException;
import org.example.photo.model.Photo;
import org.example.photo.model.PhotoComment;
import org.example.photo.model.PhotoLike;
import org.example.photo.repository.PhotoCommentRepository;
import org.example.photo.repository.PhotoLikeRepository;
import org.example.photo.repository.PhotoRepository;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.repository.UserRepository;
import org.example.user.service.UserService;
import org.example.web.dto.photo.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoLikeRepository photoLikeRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public PhotoService(PhotoRepository photoRepository,
                        PhotoLikeRepository photoLikeRepository,
                        PhotoCommentRepository photoCommentRepository,
                        UserRepository userRepository,
                        UserService userService,
                        SimpMessagingTemplate messagingTemplate) {
        this.photoRepository = photoRepository;
        this.photoLikeRepository = photoLikeRepository;
        this.photoCommentRepository = photoCommentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    public Photo uploadPhoto(String url, String description, AuthenticationDetails authenticationDetails) {
        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        Photo photo = Photo.builder()
                .user(user)
                .url(url)
                .description(description)
                .uploadedAt(LocalDateTime.now())
                .build();

        return photoRepository.save(photo);
    }

    public Integer likePhoto(UUID photoId, AuthenticationDetails authenticationDetails) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo not found!"));
        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        if (photoLikeRepository.existsByPhotoAndLikedUser(photo, user)) {
            throw new IllegalStateException("Already liked!");
        }

        PhotoLike like = PhotoLike.builder()
                .photo(photo)
                .likedUser(user)
                .build();

        photoLikeRepository.save(like);

        PhotoLikeNotification notification = PhotoLikeNotification.builder()
                .photoId(photo.getId())
                .userId(user.getId())
                .username(user.getUsername())
                .build();

        messagingTemplate.convertAndSend(
                "/topic/photo/" + photoId + "/likes", notification);

        return photoLikeRepository.countByPhoto(photo);
    }

    public PhotoComment commentPhoto(UUID photoId, String content, AuthenticationDetails authenticationDetails) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo not found!"));
        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        PhotoComment comment = PhotoComment.builder()
                .photo(photo)
                .commentedUser(user)
                .content(content)
                .commentedAt(LocalDateTime.now())
                .build();

        PhotoComment savedComment = photoCommentRepository.save(comment);

        PhotoCommentResponse message = PhotoCommentResponse.builder()
                .id(savedComment.getId())
                .photoId(savedComment.getPhoto().getId())
                .userId(savedComment.getCommentedUser().getId())
                .username(savedComment.getCommentedUser().getUsername())
                .content(savedComment.getContent())
                .commentedAt(savedComment.getCommentedAt())
                .build();

        messagingTemplate.convertAndSend(
                "/topic/photo/" + photoId + "/comments", message);

        return savedComment;
    }

    public List<PhotoRequest> getUserPhotos(AuthenticationDetails authenticationDetails,
                                            Integer limit,
                                            Integer offset) {
        User user = userRepository.findById(authenticationDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        Stream<Photo> photoStream = photoRepository.findAllByUser(user).stream();

        if (offset != null) {
            photoStream = photoStream.skip(offset);
        }
        if (limit != null) {
            photoStream = photoStream.limit(limit);
        }

        return photoStream
                .map(photo -> PhotoRequest.builder()
                        .id(photo.getId())
                        .url(photo.getUrl())
                        .description(photo.getDescription())
                        .uploadedAt(photo.getUploadedAt())
                        .likes(photo.getLikes().size())
                        .comments(photo.getComments().size())
                        .build()).toList();
    }

    public Photo getPhotoByUrl(String filename) {
        return photoRepository.findByUrl(filename)
                .orElseThrow(() -> new NotFoundException("Photo not found!"));
    }

    public String saveFileAndGetUrl(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + e.getMessage());
        }
    }

    public void deletePhoto(UUID photoId, AuthenticationDetails auth) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found!"));

        if (!photo.getUser().getId().equals(auth.getUserId())) {
            throw new RuntimeException("Access denied!");
        }

        try {
            Path filePath = Paths.get("uploads").resolve(photo.getUrl());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file!");
        }

        photoRepository.delete(photo);
    }

    public List<PhotoComment> getCommentsForPhoto(UUID photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(()  -> new NotFoundException("Photo not found!"));
        return photoCommentRepository.findAllByPhotoOrderByCommentedAtAsc(photo);
    }

    public List<PhotoResponse> getPhotosByUser(UUID userId, Integer limit, Integer offset) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        Stream<Photo> photoStream = photoRepository.findAllByUser(user).stream();

        if (offset != null) {
            photoStream = photoStream.skip(offset);
        }

        if (limit != null) {
            photoStream = photoStream.limit(limit);
        }

        return photoStream
                .map(photo -> PhotoResponse.builder()
                        .id(photo.getId())
                        .url(photo.getUrl())
                        .description(photo.getDescription())
                        .uploadedAt(photo.getUploadedAt())
                        .likes(photo.getLikes().size())
                        .comments(photo.getComments().size())
                        .build())
                .toList();
    }

    public List<PhotoLike> getLikesForPhoto(UUID photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(()  -> new NotFoundException("Photo not found!"));
        return photoLikeRepository.findAllByPhoto(photo);
    }

}

