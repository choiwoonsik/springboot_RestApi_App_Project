package com.restApi.restApiSpringBootApp.config.auth.dto;

import com.restApi.restApiSpringBootApp.domain.user.Role;
import com.restApi.restApiSpringBootApp.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String nickName;

    @Builder

    public OAuthAttributes(
            Map<String, Object> attributes,
            String nameAttributeKey, String name,
            String email, String nickName) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .nickName((String) attributes.get("nickName"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .nickName(nickName)
                .role(Role.GUEST)
                .build();
    }
}
