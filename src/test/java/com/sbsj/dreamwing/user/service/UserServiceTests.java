package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 유저 서비스 테스트 클래스
 * @author 정은찬
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ------------------------------------------------------
 * 2024.07.28  	정은찬        최초 생성
 * 2024.07.30   정은찬        로그인 메서드 테스트 작성
 * 2024.07.31   정은찬        회원탈퇴 메서드 및 회원 정보 조회 메서드 테스트 작성
 * 2024.07.31   정은찬        회원 정보 업데이트 메서드 및 로그아웃 메서드 테스트 작성
 * 2024.08.02   정은찬        로그인 아이디 존재 여부 확인 메서드 테스트 작성
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

        signUpRequestDTO.setLoginId("loginTest6");
        signUpRequestDTO.setPassword("123456");
        signUpRequestDTO.setName("testServiceName");
        signUpRequestDTO.setPhone("testPhone");
        signUpRequestDTO.setProfileImageUrl("testUrl");

        String result = userService.signUp(signUpRequestDTO);
        Assertions.assertEquals(result, "사용자 등록 성공");
    }

    @Test
    public void loginTest() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLoginId("loginTest");
        loginRequestDTO.setPassword("123456");

        try {
            String result = userService.login(loginRequestDTO);

            if(result == null || result.length() == 0) {
                log.info("JWT Token: null");
            }
            else {
                log.info("JWT Token: " + result);
            }
        } catch (Exception e) {

        }
    }

    @Test
    public void withdrawTest() {
        boolean result = userService.withdraw(4);
        Assertions.assertEquals(result, true);
    }


    @Test
    public void getUserInfoTest() {
        UserInfoDTO result = userService.getUserInfo(7);
        Assertions.assertEquals(result.getName(), "loginTest4");
    }

    @Test
    public void updateUserInfoTest() {
        long userId = 7;
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .password("test1222")
                .name("updateTest")
                .phone("11111111111")
                .profileImageUrl("updateImage")
                .build();

        Boolean result = userService.updateUserInfo(userId, userUpdateDTO);
        Assertions.assertEquals(result, true);
    }

    @Test
    public void getPointListTest() {
        List<MyPointVO> userPointList = userService.getUserPointList(8, 2, 2);
        log.info(userPointList.toString());
    }

    @Test
    public void getSupportListTest() {
        List<MySupportVO> userSupportList = userService.getUserSupportList(1, 2, 2);
        log.info(userSupportList.toString());
    }

    @Test
    public void checkExistLoginIdTest() {
        LoginIdDTO loginIdDTO = new LoginIdDTO();
        loginIdDTO.setLoginId("loginTest2");
        boolean result = userService.checkExistLoginId(loginIdDTO);

        Assertions.assertEquals(result, false);
    }
}
