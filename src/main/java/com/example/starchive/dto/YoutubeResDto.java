package com.example.starchive.dto;

import com.example.starchive.entity.Youtube;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class YoutubeResDto {

    private Long id;

    private String name;

    private String title;

    private String img;

    private String url;

    private String videoLength;

    private String views;

    private LocalDateTime uploadTime;

    public YoutubeResDto(Youtube youtube) {
        this.id = youtube.getId();
        this.name = youtube.getName();
        this.title = youtube.getTitle();
        this.img = youtube.getImg();
        this.url = youtube.getUrl();
        this.videoLength = youtube.getVideoLength();
        this.views = youtube.getViews();
        this.uploadTime = youtube.getUploadTime();
    }
}
