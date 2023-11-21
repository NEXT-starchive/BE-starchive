package com.example.starchive.dto.crawlingdto;

import com.example.starchive.entity.Youtube;

import java.time.LocalDateTime;

public class YoutubeCrawlingDto {
  private String name;

  private String title;

  private String img;

  private String url;

  private String videoLength; // added videoLength

  private LocalDateTime uploadTime;

  private String views;

  @Override
  public String toString() {
    return "\ntitle: "
        + title
        + "\nimg: "
        + img
        + "\n url: "
        + url
        + "\n uploadTime: "
        + uploadTime
        + "\n"
        + "videoLength: "
        + videoLength
        + "\n"
        + "view: "
        + views
        + "\n";
  }
  ;

  public Youtube toEntity() {
    Youtube youtube =
        Youtube.builder()
            .name("BANGTANTV")
            .title(this.title)
            .img(this.img)
            .url(this.url)
            .videoLength(this.videoLength)
            .uploadTime(this.uploadTime)
            .views(this.views)
            .build();
    return youtube;
  }

  public YoutubeCrawlingDto(
      String title, String img, String url, String videoLength, LocalDateTime uploadTime,
      String views) {
    this.name = "BANGTANTV";
    this.title = title;
    this.img = img;
    this.url = url;
    this.videoLength = videoLength;
    this.uploadTime = uploadTime;
    this.views = views;
  }
}
