package com.example.starchive.dto;

import com.example.starchive.entity.Goods;

import java.time.LocalDateTime;

public class GoodsResDto {

    private String id;

    private String shop;

    private Integer price;

    private String title;

    private String img;

    private String url;

    private LocalDateTime uploadTime;

    public GoodsResDto(Goods goods) {
        this.id = goods.getId();
        this.shop = goods.getShop();
        this.price = goods.getPrice();
        this.title = goods.getTitle();
        this.img = goods.getImg();
        this.url = goods.getUrl();
        this.uploadTime = goods.getUploadTime();
    }

}
