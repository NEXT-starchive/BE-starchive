package com.example.starchive.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Youtube {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String img;

    @Column
    private String url;

    @Column
    private LocalDateTime uploadTime;



}
