package com.restApi.restApiSpringBootApp.controller.v1;

import com.restApi.restApiSpringBootApp.entity.User;
import com.restApi.restApiSpringBootApp.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;

    @GetMapping("/users")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @PostMapping("/user")
    public User save() {
        User user = User.builder()
                .email("dnstlr2933@naver.com")
                .name("최운식")
                .build();

        return userJpaRepo.save(user);
    }

    @GetMapping("/findUser/{name}")
    public User findUserByName(@PathVariable String name) {
        return userJpaRepo.findByName(name);
    }
}
