package com.example.starchive.service;

import com.example.starchive.dto.GoodsResDto;
import com.example.starchive.entity.Goods;
import com.example.starchive.repository.GoodsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService implements CrawlingData{

    private final GoodsRepository goodsRepository;


    @Override
    public Page<GoodsResDto> getData(Pageable pageable) {
        Page<Goods> goodsList = goodsRepository.findByAll(pageable);
        Page<GoodsResDto> goodsDtoList = goodsList.map(good -> new GoodsResDto(good));
        return goodsDtoList;
    }
}
