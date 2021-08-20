package com.restApi.restApiSpringBootApp.dto.user;

import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;
    private String nickName;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
