package com.example.starchive.dto;

import com.example.starchive.entity.Twitter;
import com.example.starchive.entity.Youtube;

import java.time.LocalDateTime;

public class TwitterResDto {

    private Long id;

    private String name;

    private String content;

    private String img;

    private String url;

    private LocalDateTime uploadTime;

    public TwitterResDto(Twitter twitter) {
        this.id = twitter.getId();
        this.name = twitter.getName();
        this.content = twitter.getContent();
        this.img = twitter.getImg();
        this.url = twitter.getUrl();
        this.uploadTime = twitter.getUploadTime();
    }
}
