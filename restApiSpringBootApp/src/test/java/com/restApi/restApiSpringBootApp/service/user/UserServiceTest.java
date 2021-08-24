package com.restApi.restApiSpringBootApp.service.user;

import com.restApi.restApiSpringBootApp.advice.exception.CUserNotFoundException;
import com.restApi.restApiSpringBootApp.domain.user.User;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.domain.user.UserJpaRepo;
import com.restApi.restApiSpringBootApp.dto.user.UserResponseDto;
import com.restApi.restApiSpringBootApp.dto.sign.UserSignupRequestDto;
import com.restApi.restApiSpringBootApp.service.security.SignService;
import com.restApi.restApiSpringBootApp.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserJpaRepo userJpaRepo;
    @Autowired
    SignService signService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserSignupRequestDto getUserSignupRequestDto(int number) {
        return UserSignupRequestDto.builder()
                .name("name" + number)
                .password("password" + number)
                .email("email" + number)
                .nickName("nickName" + number)
                .build();
    }

    @Test
    public void 회원등록() {
        // given
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);
        User savedUser = userJpaRepo.save(userA.toEntity(passwordEncoder));

        // when
        UserResponseDto userB = userService.findById(savedUser.getUserId());
        User byId = userJpaRepo.findById(savedUser.getUserId()).orElseThrow(CUserNotFoundException::new);

        // then
        assertThat(userA.getName()).isEqualTo(userB.getName());
        assertThat(userA.getEmail()).isEqualTo(userB.getEmail());
        assertThat(
                userService.findById(savedUser.getUserId()).getEmail())
                .isEqualTo(byId.getEmail());
    }

    @Test
    public void 회원등록_이메일검증() {
        // given
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        // when
        UserResponseDto dtoA = userService.findById(user.getUserId());

        // then
        assertThat(userA.getEmail()).isEqualTo(dtoA.getEmail());
        assertThat(userA.getName()).isEqualTo(dtoA.getName());
    }

    @Test
    public void 전체_회원조회() {
        // given
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);
        UserSignupRequestDto userB =
                getUserSignupRequestDto(2);

        // when
        userJpaRepo.save(userA.toEntity(passwordEncoder));
        userJpaRepo.save(userB.toEntity(passwordEncoder));

        // then
        List<UserResponseDto> allUser = userService.findAllUser();
        assertThat(allUser.size()).isSameAs(2);
    }

    @Test
    public void 회원수정_닉네임() {
        // given
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        // when
        UserRequestDto updateUser = UserRequestDto.builder()
                .nickName("bbb")
                .build();
        userService.update(user.getUserId(), updateUser);

        // then
        assertThat(userService.findById(user.getUserId()).getNickName()).isEqualTo("bbb");
    }

    @Test
    public void 회원삭제() {
        // given
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);
        User user = userJpaRepo.save(userA.toEntity(passwordEncoder));

        // when
        userService.delete(user.getUserId());

        // then
        assertThrows(CUserNotFoundException.class, () -> userService.findById(user.getUserId()));
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime
                .of(2021, 8, 5, 22, 31, 0);
        UserSignupRequestDto userA =
                getUserSignupRequestDto(1);

        //when
        userJpaRepo.save(userA.toEntity(passwordEncoder));
        List<User> users = userJpaRepo.findAll();

        //then
        User firstUser = users.get(0);
        assertThat(firstUser.getCreatedDate()).isAfter(now);
        assertThat(firstUser.getModifiedDate()).isAfter(now);
    }
}