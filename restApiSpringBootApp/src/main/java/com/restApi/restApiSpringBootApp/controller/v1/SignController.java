package com.restApi.restApiSpringBootApp.controller.v1;

import com.restApi.restApiSpringBootApp.dto.jwt.TokenDto;
import com.restApi.restApiSpringBootApp.dto.jwt.TokenRequestDto;
import com.restApi.restApiSpringBootApp.dto.user.UserLoginRequestDto;
import com.restApi.restApiSpringBootApp.dto.user.UserSignupRequestDto;
import com.restApi.restApiSpringBootApp.model.response.SingleResult;
import com.restApi.restApiSpringBootApp.service.ResponseService;
import com.restApi.restApiSpringBootApp.service.security.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. SignUp/LogIn"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {

    private final SignService SignService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<TokenDto> login(
            @ApiParam(value = "로그인 요청 DTO", required = true)
            @RequestBody UserLoginRequestDto userLoginRequestDto) {

        TokenDto tokenDto = SignService.login(userLoginRequestDto);
        return responseService.getSingleResult(tokenDto);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @ApiParam(value = "회원 가입 요청 DTO", required = true)
            @RequestBody UserSignupRequestDto userSignupRequestDto) {
        Long signupId = SignService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }

    @ApiOperation(
            value = "액세스, 리프레시 토큰 재발급",
            notes = "엑세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급합니다.")
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(SignService.reissue(tokenRequestDto));
    }
}
