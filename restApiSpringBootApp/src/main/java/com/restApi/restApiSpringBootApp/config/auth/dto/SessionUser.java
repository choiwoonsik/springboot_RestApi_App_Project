package com.restApi.restApiSpringBootApp.config.auth.dto;

import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String nickName;

    public SessionUser(User user) {
        this.name = getName();
        this.email = getEmail();
        this.nickName = getNickName();
    }
}
