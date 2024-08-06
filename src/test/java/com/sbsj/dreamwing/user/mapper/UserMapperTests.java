package com.sbsj.dreamwing.user.mapper;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.UserVO;

import com.sbsj.dreamwing.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 유저 매퍼 테스트 클래스
 * @author 정은찬
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은찬         최초 생성
 * 2024.07.29   정은찬         testCheckLoginIdExistence 테스트 코드 작성
 * 2024.07.30   정은찬         testFindUserByLoginId 테스트 코드 작성
 * </pre>
 */
@Slf4j
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        UserVO userVO = UserVO.builder()
                .loginId("test2")
                .password("test12345")
                .name("testKim")
                .phone("01012341234")
                .profileImageUrl("testImage")
                .build();

        // 현재 시간을 TIMESTAMP로 설정
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());


        userVO.setCreatedDate(currentTimestamp);

        int check = userMapper.insertUser(userVO);
        Assertions.assertEquals(check, 1);
    }

    @Test
    public void checkLoginIdExistenceTest() {
        String loginId = userMapper.checkLoginIdExistence("test2");
        Assertions.assertEquals(loginId, "test2");
    }

    @Test
    public void selectUserByLoginIdTest() {
        UserDTO userDTO = userMapper.selectUserByLoginId("test2")
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));
        Assertions.assertEquals(userDTO.getUserId(), 1);
    }

    @Test
    public void withdrawTest() {
        long userId = 2;
        int result = userMapper.withdraw(userId);
        Assertions.assertEquals(result, 1);
    }

    @Test
    public void selectUserByUserIdTest() {
        UserDTO userDTO = userMapper.selectUserByUserId(7)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));
        log.info(userDTO.toString());
    }

    @Test
    public void updateUserInfoTest() {
        UserVO userVO = UserVO.builder()
                .userId(2)
                .password("test1222")
                .name("updateTest")
                .phone("11111111111")
                .profileImageUrl("updateImage")
                .build();

        int result = userMapper.updateUserInfo(userVO);
        Assertions.assertEquals(result, 1);
    }

    @Test
    public void selectTotalSupportPoint() {
        int userId = 45;
        Integer result = userMapper.selectTotalSupportPoint(userId);

        if(result == null) {
            result = 0;
        }
        Assertions.assertEquals(result, 0);
    }

//    @Test
//    public void getUserPointVOListTest() {
//        List<MyPointVO> userPointVOList = userMapper.getUserPointVOList(1);
//        log.info(userPointVOList.toString());
//    }

//    @Test
//    public void getUserSupportVOListTest() {
//        List<MySupportVO> userSupportVOList = userMapper.getUserSupportVOList(1);
//        log.info(userSupportVOList.toString());
//    }
}
