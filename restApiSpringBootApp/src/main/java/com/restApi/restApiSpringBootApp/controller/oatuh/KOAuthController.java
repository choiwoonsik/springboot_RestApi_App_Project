package com.restApi.restApiSpringBootApp.controller.oatuh;


import com.google.gson.Gson;
import com.restApi.restApiSpringBootApp.advice.exception.CCommunicationException;
import com.restApi.restApiSpringBootApp.model.response.CommonResult;
import com.restApi.restApiSpringBootApp.model.response.SingleResult;
import com.restApi.restApiSpringBootApp.service.response.ResponseService;
import com.restApi.restApiSpringBootApp.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "4. OAuth 2.0 Kakao")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth/kakao")
public class KOAuthController {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final KakaoService kakaoService;
    private final ResponseService responseService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${social.kakao.client-id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirectUri;

    @GetMapping("/login")
    public ModelAndView socialLogin(ModelAndView mav) {

        StringBuilder loginUri = new StringBuilder()
                .append(env.getProperty("social.kakao.url.login"))
                .append("?response_type=code")
                .append("&client_id=").append(kakaoClientId)
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirectUri);
        mav.addObject("loginUrl", loginUri);
        mav.setViewName("social/login");
        return mav;
    }

    @GetMapping(value = "/redirect")
    public ModelAndView redirectKakao(
            ModelAndView mav,
            @ApiParam(value = "Authorization Code", required = true)
            @RequestParam String code) {

        mav.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
        mav.setViewName("social/redirectKakao");
        return mav;
    }

    @GetMapping(value = "/unlink")
    public CommonResult unlinkKakao(@RequestParam String accessToken) {

        String unlinkUri = env.getProperty("social.kakao.url.unlink");
        if (unlinkUri == null) throw new CCommunicationException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(unlinkUri, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("unlink " + response.getBody());
            return responseService.getSuccessResult();
        }
        throw new CCommunicationException();
    }
}
