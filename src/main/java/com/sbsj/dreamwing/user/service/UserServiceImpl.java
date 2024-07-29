package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.domain.UserVO;
import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;
import com.sbsj.dreamwing.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 사용자 관련 서비스 구현체
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.06.28     	정은찬        		       최초 생성
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(SignUpRequestDTO signUpRequestDTO) {
        // loginId 중복 여부 확인
        if(userMapper.checkLoginIdExistence(signUpRequestDTO.getLoginId()) != null) {
            return "중복 아이디 존재";
        }
        // 현재 시간을 TIMESTAMP로 설정
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());

        UserVO userVO = UserVO.builder()
                .loginId(signUpRequestDTO.getLoginId())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .name(signUpRequestDTO.getName())
                .phone(signUpRequestDTO.getPhone())
                .profileImageUrl(signUpRequestDTO.getProfileImageUrl())
                .createdDate(currentTimestamp)
                .build();

        int saved = userMapper.insertUser(userVO);

        if (saved != 0) {
            return "사용자 등록 성공";
        }
        else {
            return "사용자 등록 실패";
        }
    }
}
