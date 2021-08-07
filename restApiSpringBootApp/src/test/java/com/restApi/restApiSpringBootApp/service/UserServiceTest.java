package com.restApi.restApiSpringBootApp.service;

import com.restApi.restApiSpringBootApp.advice.exception.UserNotFoundCException;
import com.restApi.restApiSpringBootApp.domain.user.Role;
import com.restApi.restApiSpringBootApp.domain.user.User;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.domain.user.UserJpaRepo;
import com.restApi.restApiSpringBootApp.dto.user.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class UserServiceTest {

    EntityManager em;
    UserService userService;
    UserJpaRepo userJpaRepo;

    @Autowired
    public UserServiceTest(EntityManager em, UserService userService, UserJpaRepo userJpaRepo) {
        this.em = em;
        this.userService = userService;
        this.userJpaRepo = userJpaRepo;
    }

    @Test
    @DisplayName("등록 테스트")
    void 등록() {
        // given
        UserRequestDto userA = UserRequestDto.builder()
                .name("최운식")
                .email("운식@이메일.com")
                .role(Role.USER)
                .build();
        Long saveId = userService.save(userA);

        // when
        UserResponseDto userB = userService.findById(saveId);

        // then
        Assertions.assertThat(userA.getName()).isEqualTo(userB.getName());
        Assertions.assertThat(userA.getEmail()).isEqualTo(userB.getEmail());
        Assertions.assertThat(
                        userService.findById(saveId).getEmail())
                .isEqualTo(userJpaRepo.findById(saveId).get().getEmail());
    }

    @Test
    @DisplayName("저장 후 이름, 이메일 확인")
    void 저장후_이메일_이름비교() {
        // given
        UserRequestDto userA = UserRequestDto.builder()
                .name("운식")
                .email("email")
                .role(Role.USER)
                .build();
        Long id = userService.save(userA);

        // when
        UserResponseDto dtoA = userService.findById(id);

        // then
        Assertions.assertThat(userA.getEmail()).isEqualTo(dtoA.getEmail());
        Assertions.assertThat(userA.getName()).isEqualTo(dtoA.getName());
    }

    @Test
    @DisplayName("모든 회원 조회")
    void 모든_회원_조회() {
        // given
        UserRequestDto userA = UserRequestDto.builder()
                .name("운식")
                .email("email")
                .role(Role.USER)
                .build();
        userService.save(userA);
        UserRequestDto userB = UserRequestDto.builder()
                .name("운식2")
                .email("email2")
                .role(Role.USER)
                .build();
        userService.save(userB);

        // when
        List<UserResponseDto> allUser = userService.findAllUser();

        // then
        Assertions.assertThat(allUser.size()).isSameAs(2);
    }

    @Test
    @DisplayName("회원 수정 - nickName")
    void 회원수정_닉네임() {
        // given
        UserRequestDto originUser = UserRequestDto.builder()
                .name("userA")
                .email("user@userA.com")
                .nickName("aaa")
                .role(Role.USER)
                .build();
        Long id = userService.save(originUser);


        // when
        UserRequestDto updateUser = UserRequestDto.builder()
                .nickName("bbb")
                .build();
        userService.update(id, updateUser);

        // then
        Assertions.assertThat(userService.findById(id).getNickName()).isEqualTo("bbb");
        System.out.println(userService.findById(id).getNickName());
    }

    @Test
    @DisplayName("회원 삭제")
    void 삭제() {
        // given
        UserRequestDto userA = UserRequestDto.builder()
                .name("woonsik")
                .email("email")
                .role(Role.USER)
                .build();
        Long saveId = userService.save(userA);

        // when
        userService.delete(saveId);

        // then
        org.junit.jupiter.api.Assertions.
                assertThrows(UserNotFoundCException.class, () -> userService.findById(saveId));
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception
    {
        //given
        LocalDateTime now = LocalDateTime
                .of(2021, 8, 5, 22, 31, 0);
        userJpaRepo.save(User.builder()
                .name("운식")
                .email("운식@이메일.com")
                .role(Role.USER)
                .build());

        //when
        List<User> users = userJpaRepo.findAll();

        //then
        User user = users.get(0);

        System.out.println(">>>>>>> createDate = " + user.getCreatedDate()
                + ", modifiedDate = " + user.getModifiedDate());

        Assertions.assertThat(user.getCreatedDate()).isAfter(now);
        Assertions.assertThat(user.getModifiedDate()).isAfter(now);
    }
}