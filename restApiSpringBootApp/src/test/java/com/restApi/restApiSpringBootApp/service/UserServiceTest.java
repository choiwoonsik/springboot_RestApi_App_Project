package com.restApi.restApiSpringBootApp.service;

import com.restApi.restApiSpringBootApp.advice.exception.UserNotFoundCException;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.entity.User;
import com.restApi.restApiSpringBootApp.repository.UserJpaRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
        new UserRequestDto();
        UserRequestDto userA = UserRequestDto.builder()
                .name("최운식")
                .email("운식@이메일.com")
                .build();
        Long saveId = userService.save(userA);

        // when
        UserRequestDto userB = userService.findById(saveId);

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
                .build();
        Long id = userService.save(userA);

        // when
        UserRequestDto dtoA = userService.findById(id);

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
                .build();
        userService.save(userA);
        UserRequestDto userB = UserRequestDto.builder()
                .name("운식2")
                .email("email2")
                .build();
        userService.save(userB);

        // when
        List<UserRequestDto> allUser = userService.findAllUser();

        // then
        Assertions.assertThat(allUser.size()).isSameAs(2);
    }

    @Test
    @DisplayName("회원 이름 수정")
    void 수정() {
        // given
        UserRequestDto uesrA = UserRequestDto.builder()
                .name("name")
                .email("email")
                .build();
        Long id = userService.save(uesrA);

        // when
        userService.modify(id, "newName");

        // then
        Assertions.assertThat(userService.findById(id).getName()).isEqualTo("newName");
    }

    @Test
    @DisplayName("회원 삭제")
    void 삭제() {
        // given
        UserRequestDto userA = UserRequestDto.builder()
                .name("woonsik")
                .email("email")
                .build();
        Long saveId = userService.save(userA);

        // when
        userService.delete(saveId);

        // then
        org.junit.jupiter.api.Assertions.
                assertThrows(UserNotFoundCException.class, () -> userService.findById(saveId));
    }
}