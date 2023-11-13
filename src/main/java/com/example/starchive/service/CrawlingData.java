package com.example.starchive.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrawlingData {

    Page<?> getData(Pageable pageable);

    public void crawlData();
}
