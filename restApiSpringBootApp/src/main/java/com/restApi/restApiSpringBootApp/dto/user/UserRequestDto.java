package com.restApi.restApiSpringBootApp.dto.user;

import com.restApi.restApiSpringBootApp.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;

    @Builder
    public UserRequestDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }
}
