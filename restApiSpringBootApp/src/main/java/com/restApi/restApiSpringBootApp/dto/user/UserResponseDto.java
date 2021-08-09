package com.restApi.restApiSpringBootApp.dto.user;

import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickName;
    private List<String> roles;
    private Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime modifiedDate;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.roles = user.getRoles();
        this.authorities = user.getAuthorities();
        this.modifiedDate = user.getModifiedDate();
    }
}
