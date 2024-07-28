package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;
import com.sbsj.dreamwing.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void signUpTest() {
        SignUpRequestDTO signUpRequestDTO  = new SignUpRequestDTO();

        signUpRequestDTO.setLoginId("testServiceID");
        signUpRequestDTO.setPassword("123456");
        signUpRequestDTO.setName("testServiceName");
        signUpRequestDTO.setPhone("testPhone");
        signUpRequestDTO.setProfileImageUrl("testUrl");

        boolean success = userService.signUp(signUpRequestDTO);
        Assertions.assertTrue(success);
    }
}
