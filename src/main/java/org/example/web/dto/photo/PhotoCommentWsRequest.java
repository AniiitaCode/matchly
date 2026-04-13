package org.example.web.dto.photo;

import lombok.Data;

import java.util.UUID;

@Data
public class PhotoCommentWsRequest {

    private UUID photoId;
    private String content;

}
