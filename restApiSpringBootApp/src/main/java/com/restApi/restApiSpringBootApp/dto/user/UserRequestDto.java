package com.restApi.restApiSpringBootApp.dto.user;

import com.restApi.restApiSpringBootApp.domain.user.Role;
import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;
    private String nickName;
    private Role role;

    @Builder
    public UserRequestDto(String email, String name, String nickName, Role role) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .role(role)
                .build();
    }
}
