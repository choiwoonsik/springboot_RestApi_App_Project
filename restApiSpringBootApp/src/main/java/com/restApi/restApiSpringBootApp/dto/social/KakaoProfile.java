package com.restApi.restApiSpringBootApp.dto.social;

import lombok.*;

@Getter
public class KakaoProfile {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    public static class KakaoAccount {
        private String email;
    }

    @Getter
    @ToString
    public static class Properties {
        private String nickname;
    }
}
/*
{
    "id":1860827414,
    "connected_at":"2021-08-22T15:22:52Z",
    "properties":
        {
            "nickname":"최운식"
        },
    "kakao_account":
        {
            "profile_nickname_needs_agreement":false,
            "profile":
                {
                    "nickname":"최운식"
                },
            "has_email":true,
            "email_needs_agreement":false,
            "is_email_valid":true,
            "is_email_verified":true,
            "email":"groom@kakao.com"
        }
}
 */
