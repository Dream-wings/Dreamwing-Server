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
 * 회원 관련 서비스 구현체
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일            수정자       				        수정내용
 * ----------  ----------------    --------------------------------------------------------------------------
 *  2024.07.28     	정은찬        		       최초 생성 및 회원가입 메서드 추가
 *  2024.07.29      정은찬                      로그인 메서드 추가
 *  2024.07.31      정은찬                      회원탈퇴 메서드 및 회원 정보 조회 메서드 추가
 *  2024.07.31      정은찬                      회원 정보 업데이트 메서드 및 로그아웃 메서드 추가
 *  2024.07.31      정은찬                      포인트 내역 조회 메서드 및 후원 내역 조회 메서드 추가
 *  2024.07.31      정은찬                      회원가입 및 회원 정보 업데이트 프로필 이미지 S3 업로드 메서드 추가
 *  2024.08.02      정은찬                      로그인 아이디 존재 여부 확인 메서드 추가
 *  2024.08.03      정은찬                      마이페이지 회원 정보 조회 메서드 추가
 *  2024.08.04      정은찬                      페이징 처리를 위해 포인트 내역, 후원 내역 조회 메서드 수정
 *  2024.08.05      정은찬                      회원 정보 업데이트 메서드 수정, 봉사 활동 내역 조회 메서드 추가
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

    /**
     * 회원가입 메서드
     * @author 정은찬
     * @param signUpRequestDTO
     * @return SignUpRequestDTO
     */
    @Transactional
    public String signUp(SignUpRequestDTO signUpRequestDTO) {
        // 로그인 ID 중복 여부 확인
        // 동일한 로그인 ID가 이미 존재하는 경우 "중복 아이디 존재" 메시지 반환
        if(userMapper.checkLoginIdExistence(signUpRequestDTO.getLoginId()) != null) {
            return "중복 아이디 존재";
        }

        // 프로필 이미지 파일 처리
        // 기본 이미지 URL 설정
        String imageUrl = "https://dreamwing.s3.ap-northeast-2.amazonaws.com/image/default.jpg";

        // 사용자가 프로필 이미지 파일을 업로드했는지 확인
        MultipartFile imageFile = signUpRequestDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            // 업로드된 이미지 파일이 존재하면 S3에 파일을 업로드하고, 해당 이미지의 URL을 가져옴
            imageUrl = s3Uploader.uploadFile(signUpRequestDTO.getImageFile());
        }

        // SignUpRequestDTO에 프로필 이미지 URL 설정
        signUpRequestDTO.setProfileImageUrl(imageUrl);

        // 현재 시간을 TIMESTAMP로 설정하여 생성일로 사용
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());

        // 회원가입 정보를 기반으로 UserVO 객체 생성
        UserVO userVO = UserVO.builder()
                .loginId(signUpRequestDTO.getLoginId())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .name(signUpRequestDTO.getName())
                .phone(signUpRequestDTO.getPhone())
                .profileImageUrl(signUpRequestDTO.getProfileImageUrl())
                .createdDate(currentTimestamp)
                .build();

        // 생성된 UserVO 객체를 DB에 삽입
        int saved = userMapper.insertUser(userVO);

        // 삽입 결과에 따라 성공 또는 실패 메시지 반환
        if (saved != 0) {
            return "사용자 등록 성공";
        }
        else {
            return "사용자 등록 실패";
        }
    }

    /**
     * 로그인 메서드
     * @author 정은찬
     * @param loginRequestDTO
     * @return LoginRequestDTO
     * @throws Exception
     */
    public String login(LoginRequestDTO loginRequestDTO) throws Exception {
        // 로그인 ID로 회원 정보를 조회, 없으면 예외 발생
        UserDTO userDTO = userMapper.selectUserByLoginId(loginRequestDTO.getLoginId())
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        // 회원 정보가 없거나, 탈퇴한 회원이라면 예외 발생
        if(userDTO == null || userDTO.getWithdraw() == 1) {
            throw new Exception("잘못된 아이디입니다.");
        }
        // 회원 정보가 존재할 경우 비밀번호를 검증
        else {
            if(!passwordEncoder.matches(loginRequestDTO.getPassword(), userDTO.getPassword())) {
                // 비밀번호가 일치하지 않으면 예외 발생
                throw new Exception("잘못된 비밀번호입니다.");
            }
            // 비밀번호가 일치할 경우
            else {
                // 관리자 계정의 경우 ROLE_ADMIN 권한 부여, 일반 사용자 계정의 경우 ROLE_USER 권한 부여
                List<String> roles = null;
                if(loginRequestDTO.getLoginId().equals("ADMIN")) {
                    roles = Collections.singletonList("ROLE_ADMIN");
                }
                else {
                    roles = Collections.singletonList("ROLE_USER");
                }
                // 회원 ID와 권한을 기반으로 JWT 토큰 생성
                String token = jwtTokenProvider.createToken(userDTO.getUserId(), roles);

                // 토큰 만료 시간 계산 후 Redis에 저장
                redisTemplate.opsForValue().set("JWT_TOKEN:" + userDTO.getUserId(), token, jwtTokenProvider.getExpiration(token).getTime() - System.currentTimeMillis(),
                        TimeUnit.MILLISECONDS);

                return token;
            }
        }
    }

    /**
     * 회원 탈퇴 메서드
     * @author 정은찬
     * @param userId
     * @return boolean
     */
    public boolean withdraw(long userId) {
        int result = userMapper.withdraw(userId);

        if(result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 회원 정보 조회 메서드
     * @author 정은찬
     * @param userId
     * @return UserInfoDTO
     */
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

    /**
     * 회원 정보 업데이트 메서드
     * @author 정은찬
     * @param userId
     * @param userUpdateDTO
     */
    public boolean updateUserInfo(long userId, UserUpdateDTO userUpdateDTO) {
        // 주어진 userId로 사용자 정보를 조회, 없으면 예외 발생
        UserDTO userDTO = userMapper.selectUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        // 업데이트할 필드들을 DTO에서 가져오고, 값이 없으면 기존 값을 유지
        String loginId = userUpdateDTO.getLoginId();
        String password = userUpdateDTO.getPassword();
        String name = userUpdateDTO.getName();
        String phone = userUpdateDTO.getPhone();
        MultipartFile imageFile = userUpdateDTO.getImageFile();
        String profileImageUrl = userUpdateDTO.getProfileImageUrl();

        // 로그인 ID가 비어 있으면 기존 값을 유지
        if(loginId == null || loginId.isEmpty()) {
            loginId = userDTO.getLoginId();
        }

        // 비밀번호가 비어 있으면 기존 값을 유지, 그렇지 않으면 암호화 처리
        if(password == null || password.isEmpty()) {
            password = userDTO.getPassword();
        }
        else {
            password = passwordEncoder.encode(userUpdateDTO.getPassword());
        }

        // 이름이 비어 있으면 기존 값을 유지
        if(name == null || name.isEmpty()) {
            name = userDTO.getName();
        }

        // 전화번호가 비어 있으면 기존 값을 유지
        if(phone == null || phone.isEmpty()) {
            phone = userDTO.getPhone();
        }

        // 프로필 이미지 URL이 비어 있으면 기존 값을 유지
        if(profileImageUrl == null || profileImageUrl.isEmpty()) {
            profileImageUrl = userDTO.getProfileImageUrl();
        }

        // 이미지 파일이 업로드된 경우, S3에 업로드하고 새로운 URL로 설정
        if (imageFile != null && !imageFile.isEmpty()) {
            profileImageUrl = s3Uploader.uploadFile(userUpdateDTO.getImageFile());
        }
        else {
            profileImageUrl = userDTO.getProfileImageUrl();
        }

        // 업데이트할 정보를 담은 UserVO 객체 생성
        UserVO userVO = UserVO.builder()
                .userId(userId)
                .loginId(loginId)
                .name(name)
                .password(password)
                .phone(phone)
                .profileImageUrl(profileImageUrl)
                .build();

        // DB에 사용자 정보 업데이트를 시도
        int result = userMapper.updateUserInfo(userVO);

        // 업데이트가 성공적으로 이루어진 경우 true, 그렇지 않으면 false 반환
        if(result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 로그아웃 메서드
     * @author 정은찬
     * @param userId
     */
    public void logout(long userId) {
        // Redis에서 해당 사용자의 JWT 토큰이 존재하는지 확인
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + userId) != null) {
            // 해당 토큰이 존재하면 삭제하여 로그아웃 처리
            redisTemplate.delete("JWT_TOKEN:" + userId); //Token 삭제
        }
    }

    /**
     * 회원 포인트 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MyPointVO>
     */
    public List<MyPointVO> getUserPointList(long userId, int page, int size) {
        // 페이지 번호와 페이지 크기를 기반으로 조회할 데이터의 시작 위치(offset) 계산
        int offset= page * size;
        // 회원의 포인트 내역을 데이터베이스에서 조회, 페이지 단위로 잘라서 반환
        List<MyPointVO> userPointList = userMapper.getUserPointVOList(userId, offset, size);
        // 조회된 포인트 내역 리스트 반환
        return userPointList;
    }

    /**
     * 회원 후원 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MySupportVO>
     */
    public List<MySupportVO> getUserSupportList(long userId, int page, int size) {
        // 페이지 번호와 페이지 크기를 기반으로 조회할 데이터의 시작 위치(offset) 계산
        int offset= page * size;
        // 회원의 후원 내역을 데이터베이스에서 조회, 페이지 단위로 잘라서 반환
        List<MySupportVO> userSupportList = userMapper.getUserSupportVOList(userId, offset, size);
        // 조회된 후원 내역 리스트 반환
        return userSupportList;
    }

    /**
     * 회원 봉사 활동 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MyVolunteerVO>
     */
    public List<MyVolunteerVO> getUserVolunteerList(long userId, int page, int size) {
        // 페이지 번호와 페이지 크기를 기반으로 조회할 데이터의 시작 위치(offset) 계산
        int offset = page * size;
        // 회원의 봉사 활동 내역을 데이터베이스에서 조회, 페이지 단위로 잘라서 반환
        List<MyVolunteerVO> userVolunteerList = userMapper.getUserVolunteerVOList(userId, offset, size);
        // 조회된 봉사 활동 내역 리스트 반환
        return userVolunteerList;
    }

    /**
     * 로그인 아이디 존재 여부 확인 메서드
     * @author 정은찬
     * @param loginIdDTO
     * @return Boolean
     */
    public Boolean checkExistLoginId(LoginIdDTO loginIdDTO) {
        // 주어진 로그인 ID로 DB에서 존재 여부를 확인
        String result = userMapper.checkLoginIdExistence(loginIdDTO.getLoginId());

        // 조회 결과가 null이거나 비어 있으면, 로그인 ID가 존재하지 않으므로 true 반환
        if (result == null || result.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 마이페이지 회원 정보 조회 메서드
     * @author 정은찬
     * @param userId
     * @return MyPageDTO
     */
    public MyPageDTO getMyPageInfo(long userId) {
        // 주어진 userId로 사용자 정보를 조회, 없으면 예외 발생
        UserDTO userDTO = userMapper.selectUserByUserId(userId)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        // 회원의 총 후원 포인트 조회
        Integer totalSupportPoint = userMapper.selectTotalSupportPoint(userId);

        // 총 후원 포인트가 null이면 0으로 초기화
        if(totalSupportPoint==null) {
            totalSupportPoint = 0;
        }

        // 조회한 정보를 기반으로 MyPageDTO 객체 생성
        MyPageDTO myPageDTO = MyPageDTO.builder()
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .profileImageUrl(userDTO.getProfileImageUrl())
                .totalPoint(userDTO.getTotalPoint())
                .totalSupportPoint(totalSupportPoint)
                .build();

        // 생성된 MyPageDTO 객체 반환
        return myPageDTO;
    }
}
