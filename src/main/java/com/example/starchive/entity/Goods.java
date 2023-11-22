package com.example.starchive.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import net.bytebuddy.asm.Advice.Local;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Goods {
  @Id private String id;

  @Column private String shop;

  @Column private String price;

  @Column private String title;

  @Column private String img;

  @Column private String url;

  @Column private String area;

  @Column private LocalDateTime uploadTime;

  @Builder
  public Goods(
      String shop,
      String price,
      String title,
      String img,
      String url,
      String area,
      LocalDateTime uploadTime) {
    this.shop = shop;
    this.price = price;
    this.title = title;
    this.img = img;
    this.url = url;
    this.area = area;
    this.uploadTime = uploadTime;
  }
}
