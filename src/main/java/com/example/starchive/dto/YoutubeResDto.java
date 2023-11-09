package com.example.starchive.dto;

import com.example.starchive.entity.Youtube;

import java.time.LocalDateTime;

public class YoutubeResDto {

    private String id;

    private String name;

    private String title;

    private String img;

    private String url;

    private LocalDateTime uploadTime;

    public YoutubeResDto(Youtube youtube) {
        this.id = youtube.getId();
        this.name = youtube.getName();
        this.title = youtube.getTitle();
        this.img = youtube.getImg();
        this.url = youtube.getUrl();
        this.uploadTime = youtube.getUploadTime();
    }
}
