package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.dto.LoginDTO;
import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;
import com.sbsj.dreamwing.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 유저 서비스 테스트 클래스
 * @author 정은찬
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은찬        최초 생성
 * </pre>
 */
@Slf4j
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void signUpTest() {
        SignUpRequestDTO signUpRequestDTO  = new SignUpRequestDTO();

        signUpRequestDTO.setLoginId("loginTest");
        signUpRequestDTO.setPassword("123456");
        signUpRequestDTO.setName("testServiceName");
        signUpRequestDTO.setPhone("testPhone");
        signUpRequestDTO.setProfileImageUrl("testUrl");

        String result = userService.signUp(signUpRequestDTO);
        Assertions.assertEquals(result, "사용자 등록 성공");
    }

    @Test
    public void loginTest() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLoginId("loginTest");
        loginDTO.setPassword("123456");

        String result = userService.login(loginDTO);

        if(result == null || result.length() == 0) {
            log.info("JWT Token: null");
        }
        else {
            log.info("JWT Token: " + result);
        }

    }
}
