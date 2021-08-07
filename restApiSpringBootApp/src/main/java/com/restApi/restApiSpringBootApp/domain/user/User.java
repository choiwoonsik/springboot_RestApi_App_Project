package com.restApi.restApiSpringBootApp.domain.user;

import com.restApi.restApiSpringBootApp.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User updateNickName(String nickName) {
        this.nickName = nickName;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
