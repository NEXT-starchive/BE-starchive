package com.example.starchive.service;


import com.example.starchive.entity.User;
import com.example.starchive.exception.CustomException;
import com.example.starchive.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void updateTextBalloon(String id, String textBallon) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException("오류입니다."));

        user.changeTextBalloon(textBallon);

        userRepository.save(user);
    }

    public void updateFirstDay(String id, LocalDateTime firstDay) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException("오류입니다."));

        user.changeFirstday(firstDay);

        userRepository.save(user);
    }

    public String getTextBalloon(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException("오류입니다."));

        return user.getTextballoon();
    }

    public Integer getFirstday(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException("오류입니다."));

        LocalDateTime currentTime = LocalDateTime.now();
        Long diffDay = ChronoUnit.DAYS.between(user.getFirstday(), currentTime);

        return diffDay.intValue();
    }


}