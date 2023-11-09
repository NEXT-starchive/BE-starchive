package com.example.starchive.repository;

import com.example.starchive.entity.Twitter;
import com.example.starchive.entity.Youtube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterRepository extends JpaRepository<Twitter, String> {

    Page<Twitter> findByAll(Pageable pageable);

}
