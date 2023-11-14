package com.example.starchive.dto.crawlingdto;


import com.example.starchive.entity.Twitter;

import java.time.LocalDateTime;

public class TweetsCrawlingDto {
    private String content;
    private String img;
    private LocalDateTime time;
    private String url;

    public TweetsCrawlingDto(String content, String url, String img, LocalDateTime time){
        this.content = content;
        this.img = img;
        this.time = time;
        this.url = url;
    }
    @Override
    public String toString() {
        return "content: " + content + "\nsrc: " + img + "\n" + "at: " + time + "\n\n\n";
    }

    public Twitter toEntity() {
        Twitter twitter = Twitter.builder().name("BTS_official")
                                            .content(this.content)
                                            .img(this.img)
                                            .uploadTime(this.time)
                                            .url(this.url)
                                            .build();
        return twitter;
    }

}
