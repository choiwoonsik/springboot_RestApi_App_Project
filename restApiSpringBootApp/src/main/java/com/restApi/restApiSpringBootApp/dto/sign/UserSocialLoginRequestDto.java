package com.restApi.restApiSpringBootApp.dto.sign;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialLoginRequestDto {
    private String accessToken;
}
