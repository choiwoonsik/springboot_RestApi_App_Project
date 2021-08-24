package com.restApi.restApiSpringBootApp.service.user;

import com.restApi.restApiSpringBootApp.dto.social.KakaoProfile;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "mockUser")
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    private static final String accessToken = "cLDCR4xs1TYyrJAIzQ9bzvXuzML37QiimPVuEwo9dVoAAAF7dvoLNw";

    @Test
    public void 액세스토큰으로_사용자정보_요청() throws Exception
    {
        //given
        //when
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);

        //then
        assertThat(kakaoProfile).isNotNull();
        assertThat(kakaoProfile.getKakao_account().getEmail()).isEqualTo("groom@kakao.com");
        assertThat(kakaoProfile.getProperties().getNickname()).isEqualTo("최운식");
    }
}