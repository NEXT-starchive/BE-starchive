package com.example.starchive.dto;

import com.example.starchive.entity.Twitter;
import com.example.starchive.entity.Youtube;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TwitterResDto {

    private String id;

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
