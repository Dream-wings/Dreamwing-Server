package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.support.mapper.SupportMapper;
import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.UserVO;
import com.sbsj.dreamwing.user.domain.MyVolunteerVO;
import com.sbsj.dreamwing.user.dto.*;
import com.sbsj.dreamwing.user.mapper.UserMapper;
import com.sbsj.dreamwing.util.JwtTokenProvider;
import com.sbsj.dreamwing.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 사용자 관련 서비스 구현체
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.28     	정은찬        		        최초 생성 및 회원가입 기능
 *  2024.07.29      정은찬                       로그인 기능
 *  2024.07.31      정은찬                      회원탈퇴 기능 및 회원 정보 가져오기 기능 추가
 *  2024.07.31      정은찬                      회원 정보 업데이트 기능 및 로그아웃 기능 추가
 *  2024.07.31      정은찬                      포인트 내역 조회 기능 및 후원 내역 조회 기능 추가
 *  2024.07.31      정은찬                      회원가입 및 회원 정보 업데이트 프로필 이미지 S3 업로드 기능 추가
 *  2024.08.02      정은찬                      로그인 아이디 존재 여부 확인 기능 추가
 *  2024.08.03      정은찬                      마이페이지 사용자 정보 조회 기능 추가
 *  2024.08.04      정은찬                      페이징 처리를 위해 포인트 내역, 후원 내역 조회 기능 수정
 *  2024.08.05      정은찬                      회원 정보 업데이트 기능 수정, 봉사 활동 내역 조회 기능 추가
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final S3Uploader s3Uploader;

    private final SupportMapper supportMapper;

    @Transactional
    public String signUp(SignUpRequestDTO signUpRequestDTO) {
        // loginId 중복 여부 확인
        if(userMapper.checkLoginIdExistence(signUpRequestDTO.getLoginId()) != null) {
            return "중복 아이디 존재";
        }

        // 이미지 파일 처리
        String imageUrl = "https://dreamwing.s3.ap-northeast-2.amazonaws.com/image/default.jpg";

        MultipartFile imageFile = signUpRequestDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3Uploader.uploadFile(signUpRequestDTO.getImageFile());
        }

        signUpRequestDTO.setProfileImageUrl(imageUrl);

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

    public String login(LoginRequestDTO loginRequestDTO) throws Exception {
        UserDTO userDTO = userMapper.selectUserByLoginId(loginRequestDTO.getLoginId())
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        if(userDTO == null || userDTO.getWithdraw() == 1) {
            throw new Exception("잘못된 아이디입니다.");
        }
        else {
            if(!passwordEncoder.matches(loginRequestDTO.getPassword(), userDTO.getPassword())) {
                throw new Exception("잘못된 비밀번호입니다.");
            }
            else {
                List<String> roles = null;
                if(loginRequestDTO.getLoginId().equals("ADMIN")) {
                    roles = Collections.singletonList("ROLE_ADMIN");
                }
                else {
                    roles = Collections.singletonList("ROLE_USER");
                }

                String token = jwtTokenProvider.createToken(userDTO.getUserId(), roles);

                //**로그아웃 구분하기 위해 redis에 저장**
                redisTemplate.opsForValue().set("JWT_TOKEN:" + userDTO.getUserId(), token, jwtTokenProvider.getExpiration(token).getTime() - System.currentTimeMillis(),
                        TimeUnit.MILLISECONDS);

                return token;
            }
        }
    }

    public boolean withdraw(long userId) {
        int result = userMapper.withdraw(userId);

        if(result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public UserInfoDTO getUserInfo(long userId) {
        UserDTO userDTO = userMapper.selectUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .build();

        return userInfoDTO;
    }

    public boolean updateUserInfo(long userId, UserUpdateDTO userUpdateDTO) {
        UserDTO userDTO = userMapper.selectUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        String loginId = userUpdateDTO.getLoginId();
        String password = userUpdateDTO.getPassword();
        String name = userUpdateDTO.getName();
        String phone = userUpdateDTO.getPhone();
        MultipartFile imageFile = userUpdateDTO.getImageFile();
        String profileImageUrl = userUpdateDTO.getProfileImageUrl();

        if(loginId == null || loginId.isEmpty()) {
            loginId = userDTO.getLoginId();
        }
        if(password == null || password.isEmpty()) {
            password = userDTO.getPassword();
        }
        else {
            password = passwordEncoder.encode(userUpdateDTO.getPassword());
        }
        if(name == null || name.isEmpty()) {
            name = userDTO.getName();
        }
        if(phone == null || phone.isEmpty()) {
            phone = userDTO.getPhone();
        }
        if(profileImageUrl == null || profileImageUrl.isEmpty()) {
            profileImageUrl = userDTO.getProfileImageUrl();
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            profileImageUrl = s3Uploader.uploadFile(userUpdateDTO.getImageFile());
        }
        else {
            profileImageUrl = userDTO.getProfileImageUrl();
        }

        UserVO userVO = UserVO.builder()
                .userId(userId)
                .loginId(loginId)
                .name(name)
                .password(password)
                .phone(phone)
                .profileImageUrl(profileImageUrl)
                .build();

        int result = userMapper.updateUserInfo(userVO);

        if(result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public void logout(long userId) {
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + userId) != null) {
            redisTemplate.delete("JWT_TOKEN:" + userId); //Token 삭제
        }

    }

    public List<MyPointVO> getUserPointList(long userId, int page, int size) {
        int offset= page * size;

        List<MyPointVO> userPointList = userMapper.getUserPointVOList(userId, offset, size);
        return userPointList;
    }

    public List<MySupportVO> getUserSupportList(long userId, int page, int size) {
        int offset= page * size;
        List<MySupportVO> userSupportList = userMapper.getUserSupportVOList(userId, offset, size);
        return userSupportList;
    }

    public List<MyVolunteerVO> getUserVolunteerList(long userId, int page, int size) {
        int offset = page * size;
        List<MyVolunteerVO> userVolunteerList = userMapper.getUserVolunteerVOList(userId, offset, size);

        return userVolunteerList;
    }

    public Boolean checkExistLoginId(LoginIdDTO loginIdDTO) {
        String result = userMapper.checkLoginIdExistence(loginIdDTO.getLoginId());

        if (result == null || result.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    public MyPageDTO getMyPageInfo(long userId) {
        UserDTO userDTO = userMapper.selectUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        Integer totalSupportPoint = userMapper.selectTotalSupportPoint(userId);

        if(totalSupportPoint==null) {
            totalSupportPoint = 0;
        }
        MyPageDTO myPageDTO = MyPageDTO.builder()
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .totalPoint(userDTO.getTotalPoint())
                .totalSupportPoint(totalSupportPoint)
                .build();

        return myPageDTO;
    }
}
