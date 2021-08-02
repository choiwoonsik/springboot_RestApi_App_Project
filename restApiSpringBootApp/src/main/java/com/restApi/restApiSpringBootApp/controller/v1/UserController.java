package com.restApi.restApiSpringBootApp.controller.v1;

import com.restApi.restApiSpringBootApp.advice.exception.UserNotFoundCException;
import com.restApi.restApiSpringBootApp.entity.User;
import com.restApi.restApiSpringBootApp.model.response.CommonResult;
import com.restApi.restApiSpringBootApp.model.response.ListResult;
import com.restApi.restApiSpringBootApp.model.response.SingleResult;
import com.restApi.restApiSpringBootApp.repository.UserJpaRepo;
import com.restApi.restApiSpringBootApp.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public SingleResult<User> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId) {
        return responseService
                .getSingleResult(userJpaRepo.findById(userId).orElseThrow(UserNotFoundCException::new));
    }

    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/email/{email}")
    public SingleResult<User> findUserByEmail
            (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email) {
        User user = userJpaRepo.findByEmail(email);
        if (user == null) throw new UserNotFoundCException();
        else return responseService
                .getSingleResult(user);
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<User> findAllUser() {
        return responseService
                .getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public SingleResult<User> save(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam Long userId,
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userJpaRepo.deleteById(userId);
        return responseService.getSuccessResult();
    }
}
