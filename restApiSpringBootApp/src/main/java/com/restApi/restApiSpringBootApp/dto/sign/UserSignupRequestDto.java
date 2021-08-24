package com.restApi.restApiSpringBootApp.dto.sign;

import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String provider;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickName(nickName)
                .name(name)
                .provider(provider)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
