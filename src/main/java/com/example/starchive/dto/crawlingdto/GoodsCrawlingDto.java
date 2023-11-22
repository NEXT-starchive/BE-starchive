package com.example.starchive.dto.crawlingdto;

import com.example.starchive.entity.Goods;
import java.time.LocalDateTime;
import lombok.ToString;

public class GoodsCrawlingDto {
  private String shop;
  private String price;
  private String title;
  private String img;
  private String url;
  private String area;
  private LocalDateTime uploadTime;

  public GoodsCrawlingDto(
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

  public Goods toEntity() {
    Goods goods =
        Goods.builder()
            .shop(this.shop)
            .price(this.price)
            .title(this.title)
            .img(this.img)
            .url(this.url)
            .area(this.area)
            .uploadTime(this.uploadTime)
            .build();
    return goods;
  }

  @Override
  public String toString(){
    return "shop: "
        + shop
        + "\n price "
        + price
        + "\ntitle: "
        + title
        + "\n img: "
        + img
        + "\n url: "
        + url
        + "\n: area: "
        + area
        + "\nuploadtime: "
        + uploadTime
        + "\n";
  }

}
