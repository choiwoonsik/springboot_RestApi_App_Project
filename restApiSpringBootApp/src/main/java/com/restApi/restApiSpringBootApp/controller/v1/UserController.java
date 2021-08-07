package com.restApi.restApiSpringBootApp.controller.v1;

import com.restApi.restApiSpringBootApp.domain.user.Role;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.dto.user.UserResponseDto;
import com.restApi.restApiSpringBootApp.model.response.CommonResult;
import com.restApi.restApiSpringBootApp.model.response.ListResult;
import com.restApi.restApiSpringBootApp.model.response.SingleResult;
import com.restApi.restApiSpringBootApp.service.ResponseService;
import com.restApi.restApiSpringBootApp.service.UserService;
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

    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public SingleResult<UserResponseDto> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findById(userId));
    }

    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/email/{email}")
    public SingleResult<UserResponseDto> findUserByEmail
            (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findByEmail(email));
    }

    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<UserResponseDto> findAllUser() {
        return responseService.getListResult(userService.findAllUser());
    }

    @ApiOperation(value = "회원 등록", notes = "회원을 등록합니다.")
    @PostMapping("/user")
    public SingleResult<Long> save(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        UserRequestDto user = UserRequestDto.builder()
                .email(email)
                .name(name)
                .role(Role.USER)
                .build();
        return responseService.getSingleResult(userService.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public SingleResult<Long> update (
            @ApiParam(value = "회원 ID", required = true) @RequestParam Long userId,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String nickName) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .nickName(nickName)
                .build();
        return responseService.getSingleResult(userService.update(userId, userRequestDto));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult();
    }
}
