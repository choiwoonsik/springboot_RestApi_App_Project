package com.restApi.restApiSpringBootApp.controller.exception;

import com.restApi.restApiSpringBootApp.advice.exception.CAccessDeniedException;
import com.restApi.restApiSpringBootApp.advice.exception.CAuthenticationEntryPointException;
import com.restApi.restApiSpringBootApp.model.response.CommonResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Exception"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entryPoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping("/accessDenied")
    public CommonResult accessDeniedException() {
        throw new CAccessDeniedException();
    }
}
