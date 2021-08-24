package com.restApi.restApiSpringBootApp.dto.social;

import lombok.Getter;

@Getter
public class RetKakaoOAuth {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
    private String scope;
}
