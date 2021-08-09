package com.restApi.restApiSpringBootApp.advice;

import com.restApi.restApiSpringBootApp.advice.exception.CAuthenticationEntryPointException;
import com.restApi.restApiSpringBootApp.advice.exception.CEmailLoginFailedException;
import com.restApi.restApiSpringBootApp.advice.exception.CEmailSignupFailedException;
import com.restApi.restApiSpringBootApp.advice.exception.CUserNotFoundException;
import com.restApi.restApiSpringBootApp.model.response.CommonResult;
import com.restApi.restApiSpringBootApp.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    /***
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info(String.valueOf(e));
        return responseService.getFailResult
                (Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    /***
     * 유저를 찾지 못했을 때 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg")
        );
    }

    /***
     * 유저 이메일 로그인 실패 시 발생시키는 예외
     */
    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, CEmailLoginFailedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailLoginFailed.code")), getMessage("emailLoginFailed.msg")
        );
    }

    /***
     * 회원 가입 시 이미 로그인 된 이메일인 경우 발생 시키는 예외
     */
    @ExceptionHandler(CEmailSignupFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSignupFailedException(HttpServletRequest request, CEmailSignupFailedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("emailSignupFailed.code")), getMessage("emailSignupFailed.msg")
        );
    }

    /**
     * 전달한 Jwt 이 정상적이지 않은 경우 발생 시키는 예외
     */
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntrypointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("authenticationEntrypoint.code")), getMessage("authenticationEntrypoint.msg")
        );
    }

    /**
     * 권한이 없는 리소스를 요청한 경우 발생 시키는 예외
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(
                Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg")
        );
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}