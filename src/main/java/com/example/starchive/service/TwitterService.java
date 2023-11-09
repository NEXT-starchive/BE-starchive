package com.example.starchive.service;

import com.example.starchive.dto.GoodsResDto;
import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.entity.Goods;
import com.example.starchive.entity.Twitter;
import com.example.starchive.repository.GoodsRepository;
import com.example.starchive.repository.TwitterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterService implements CrawlingData{

    private final TwitterRepository twitterRepository;

    @Override
    public Page<TwitterResDto> getData(Pageable pageable) {
        Page<Twitter> goodsList = twitterRepository.findByAll(pageable);
        Page<TwitterResDto> goodsDtoList = goodsList.map(twit -> new TwitterResDto(twit));
        return goodsDtoList;
    }
}