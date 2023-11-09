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
public class User {

    @Id
    private String userId;

    @Column
    private String textballoon;

    @Column
    private String name;

    @Column
    private LocalDateTime firstday;

    public void changeTextBalloon(String textballoon) {
        this.textballoon = textballoon;
    }

    public void changeFirstday(LocalDateTime firstday) {
        this.firstday = firstday;
    }


}
