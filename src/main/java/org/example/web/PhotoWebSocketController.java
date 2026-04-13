package org.example.web;

import org.example.photo.service.PhotoService;
import org.example.security.AuthenticationDetails;
import org.example.web.dto.photo.PhotoActionRequest;
import org.example.web.dto.photo.PhotoCommentWsRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class PhotoWebSocketController {


    private final PhotoService photoService;

    public PhotoWebSocketController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @MessageMapping("/photo.like")
    public void likePhoto(@Payload PhotoActionRequest request,
                          @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        photoService.likePhoto(request.getPhotoId(), authenticationDetails);
    }

    @MessageMapping("/photo.comment")
    public void commentPhoto(@Payload PhotoCommentWsRequest request,
                             @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        photoService.commentPhoto(request.getPhotoId(), request.getContent(), authenticationDetails);
    }

}

