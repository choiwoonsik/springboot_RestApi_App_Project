package com.restApi.restApiSpringBootApp.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {

    @GetMapping("/helloWorld/string")
    @ResponseBody
    public String helloWorldString() {
        return "hello";
    }

    @GetMapping("/helloWorld/json")
    @ResponseBody
    public Hello helloWorldJson() {
        Hello hello = new Hello();
        hello.setMessage("Hello");
        return hello;
    }

    @GetMapping("/helloWorld/page")
    public String HelloWorldPage() {
        return "helloWorld";
    }

    @Getter
    @Setter
    public static class Hello {
        private String message;
    }
}
