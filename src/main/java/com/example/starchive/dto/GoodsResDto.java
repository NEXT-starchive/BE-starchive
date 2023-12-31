package com.example.starchive.dto;

import com.example.starchive.entity.Goods;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GoodsResDto {

    private Long id;

    private String shop;

    private String price;

    private String title;

    private String img;

    private String url;

    private String area;

    private LocalDateTime uploadTime;

    public GoodsResDto(Goods goods) {
        this.id = goods.getId();
        this.shop = goods.getShop();
        this.price = goods.getPrice();
        this.title = goods.getTitle();
        this.img = goods.getImg();
        this.url = goods.getUrl();
        this.area = goods.getArea();
        this.uploadTime = goods.getUploadTime();
    }

}
