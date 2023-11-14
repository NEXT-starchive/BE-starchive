package com.example.starchive.service;

import com.example.starchive.dto.TwitterResDto;
import com.example.starchive.dto.YoutubeResDto;
import com.example.starchive.entity.Youtube;
import com.example.starchive.repository.YoutubeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class YoutubeService implements CrawlingData{

    private final YoutubeRepository youtubeRepository;

    @Override
    public Page<YoutubeResDto> getData(Pageable pageable) {
        Page<Youtube> goodsList = youtubeRepository.findAll(pageable);
        Page<YoutubeResDto> goodsDtoList = goodsList.map(tube -> new YoutubeResDto(tube));
        return goodsDtoList;
    }
    @Override
    public void crawlData(){

    }
}