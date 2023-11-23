package com.example.starchive.config.auth;

import lombok.Data;

@Data
public class GoogleUserInfo {
    private String id;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
