package com.example.starchive.repository;

import com.example.starchive.entity.Youtube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YoutubeRepository extends JpaRepository<Youtube, String> {

    Page<Youtube> findAllOrderByUploadTimeDesc(Pageable pageable);


}
