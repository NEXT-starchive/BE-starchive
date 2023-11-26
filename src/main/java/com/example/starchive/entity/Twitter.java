package com.example.starchive.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Twitter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;;

  @Column private String name;

  @Column private String content;

  @Column private String img;

  @Column private String url;

  @Column private LocalDateTime uploadTime;

  @Builder
  public Twitter(String name, String content, String img, String url, LocalDateTime uploadTime) {
    this.name = name;
    this.content = content;
    this.img = img;
    this.url = url;
    this.uploadTime = uploadTime;
  }
}
