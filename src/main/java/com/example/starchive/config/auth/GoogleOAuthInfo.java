package com.example.starchive.config.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleOAuthInfo {
    private String access_token;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}