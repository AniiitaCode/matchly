package org.example.web;

import org.example.photo.model.Photo;
import org.example.photo.model.PhotoComment;
import org.example.photo.model.PhotoLike;
import org.example.photo.service.PhotoService;
import org.example.security.AuthenticationDetails;
import org.example.user.model.user.User;
import org.example.user.service.UserService;
import org.example.web.dto.photo.*;
import org.example.web.dto.user.UserDto;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final UserService userService;

    public PhotoController(PhotoService photoService,
                           UserService userService) {
        this.photoService = photoService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PhotoRequest> uploadPhoto(@RequestParam(required = false) String url,
                                                    @RequestParam(required = false) MultipartFile file,
                                                    @RequestParam(required = false) String description,
                                                    @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        String finalUrl;

        if (file != null && !file.isEmpty()) {
            finalUrl = photoService.saveFileAndGetUrl(file);
        } else if (url != null && !url.isEmpty()) {
            finalUrl = url;
        } else {
            throw new IllegalArgumentException("Either url or file must be provided!");
        }

        Photo photo = photoService.uploadPhoto(finalUrl, description, authenticationDetails);

        PhotoRequest photoRequest = PhotoRequest.builder()
                .id(photo.getId())
                .url("/api/photos/file/" + photo.getUrl())
                .description(photo.getDescription())
                .uploadedAt(photo.getUploadedAt())
                .likes(photo.getLikes() != null ? photo.getLikes().size() : 0)
                .comments(photo.getComments() != null ? photo.getComments().size() : 0)
                .user(UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(photoRequest);
    }

    @GetMapping
    public ResponseEntity<List<PhotoRequest>> getUserPhotos(@AuthenticationPrincipal AuthenticationDetails authenticationDetails,
                                                            @RequestParam(required = false) Integer limit,
                                                            @RequestParam(required = false) Integer offset) {

        List<PhotoRequest> photos = photoService.getUserPhotos(authenticationDetails, limit, offset);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PhotoResponse>> getPhotosByUser(@PathVariable UUID userId,
                                                               @RequestParam(required = false) Integer limit,
                                                               @RequestParam(required = false) Integer offset) {
        List<PhotoResponse> photos = photoService.getPhotosByUser(userId, limit, offset);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getPhotoFile(
            @PathVariable String filename,
            @AuthenticationPrincipal AuthenticationDetails auth) throws MalformedURLException {

        Path file = Paths.get("uploads").resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping("/{photoId}/likes")
    public ResponseEntity<Integer> likePhoto(@PathVariable UUID photoId,
                                          @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        try {
            int likesCount = photoService.likePhoto(photoId, authenticationDetails);
            return ResponseEntity.ok(likesCount);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{photoId}/likes")
    public  ResponseEntity<List<PhotoLikeResponse>>  getLikes(@PathVariable UUID photoId) {
        List<PhotoLike> likes = photoService.getLikesForPhoto(photoId);

        List<PhotoLikeResponse> response = likes.stream().map(like ->
                PhotoLikeResponse.builder()
                        .userId(like.getLikedUser().getId())
                        .username(like.getLikedUser().getUsername())
                        .profilePicture(like.getLikedUser().getProfilePicture())
                        .build()).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{photoId}/comments")
    public ResponseEntity<List<PhotoCommentResponse>> getComments(@PathVariable UUID photoId) {
        List<PhotoComment> comments = photoService.getCommentsForPhoto(photoId);

        List<PhotoCommentResponse> response = comments.stream().map(comment ->
                PhotoCommentResponse.builder()
                        .id(comment.getId())
                        .photoId(comment.getPhoto().getId())
                        .userId(comment.getCommentedUser().getId())
                        .username(comment.getCommentedUser().getUsername())
                        .content(comment.getContent())
                        .commentedAt(comment.getCommentedAt())
                        .build()
        ).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{photoId}/comments")
    public ResponseEntity<PhotoCommentResponse> commentPhoto(@PathVariable UUID photoId,
                                                             @RequestBody PhotoCommentRequest photoCommentRequest,
                                                             @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        PhotoComment comment =
                photoService.commentPhoto(photoId, photoCommentRequest.getContent(), authenticationDetails);

        PhotoCommentResponse responseDto = PhotoCommentResponse.builder()
                .id(comment.getId())
                .photoId(comment.getPhoto().getId())
                .userId(comment.getCommentedUser().getId())
                .username(comment.getCommentedUser().getUsername())
                .content(comment.getContent())
                .commentedAt(comment.getCommentedAt())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);

    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable UUID photoId,
            @AuthenticationPrincipal AuthenticationDetails auth) {

        photoService.deletePhoto(photoId, auth);
        return ResponseEntity.noContent().build();
    }
}

