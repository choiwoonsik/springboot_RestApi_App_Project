package com.restApi.restApiSpringBootApp.controller.v1;

import com.restApi.restApiSpringBootApp.config.security.JwtProvider;
import com.restApi.restApiSpringBootApp.dto.user.UserLoginResponseDto;
import com.restApi.restApiSpringBootApp.dto.user.UserSignupRequestDto;
import com.restApi.restApiSpringBootApp.model.response.SingleResult;
import com.restApi.restApiSpringBootApp.service.ResponseService;
import com.restApi.restApiSpringBootApp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. SignUp/LogIn"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @GetMapping("/login")
    public SingleResult<String> login(
            @ApiParam(value = "로그인 아이디 : 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "로그인 비밀번호", required = true) @RequestParam String password) {
        UserLoginResponseDto userLoginDto = userService.login(email, password);

        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getUserId()), userLoginDto.getRoles());
        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @ApiParam(value = "회원 가입 아이디 : 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 가입 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 가입 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 가입 닉네임", required = true) @RequestParam String nickName) {

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .build();
        Long signupId = userService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }
}
