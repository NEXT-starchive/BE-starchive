package com.example.starchive.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Youtube {

  @Id private String id;

  @Column private String name;

  @Column private String title;

  @Column private String img;

  @Column private String url;

  @Column private String videoLength; // added videoLength

  @Column private String views; // added views

  @Column private LocalDateTime uploadTime;

  @Builder
  public Youtube(
      String name,
      String title,
      String img,
      String url,
      String videoLength,
      LocalDateTime uploadTime,
      String views) {
    this.name = name;
    this.title = title;
    this.img = img;
    this.url = url;
    this.videoLength = videoLength;
    this.uploadTime = uploadTime;
    this.views = views;
  }
}
