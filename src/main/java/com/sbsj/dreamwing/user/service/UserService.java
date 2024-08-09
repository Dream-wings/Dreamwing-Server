package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.MyVolunteerVO;
import com.sbsj.dreamwing.user.dto.*;

import java.util.List;

/**
 * 회원 관련 서비스 interface
 * @apiNote 회원 정보 저장 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
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
public interface UserService {
    /**
     * 회원가입 메서드
     * @author 정은찬
     * @param signUpRequestDTO
     * @return SignUpRequestDTO
     */
    public String signUp(SignUpRequestDTO signUpRequestDTO);

    /**
     * 로그인 메서드
     * @author 정은찬
     * @param loginRequestDTO
     * @return LoginRequestDTO
     * @throws Exception
     */
    public String login(LoginRequestDTO loginRequestDTO) throws Exception;

    /**
     * 회원 탈퇴 메서드
     * @author 정은찬
     * @param userId
     * @return boolean
     */
    public boolean withdraw(long userId);

    /**
     * 회원 정보 조회 메서드
     * @author 정은찬
     * @param userId
     * @return UserInfoDTO
     */
    public UserInfoDTO getUserInfo(long userId);

    /**
     * 회원 정보 업데이트 메서드
     * @author 정은찬
     * @param userId
     * @param userUpdateDTO
     */
    public boolean updateUserInfo(long userId, UserUpdateDTO userUpdateDTO);

    /**
     * 로그아웃 메서드
     * @author 정은찬
     * @param userId
     */
    public void logout(long userId);

    /**
     * 회원 포인트 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MyPointVO>
     */
    public List<MyPointVO> getUserPointList(long userId, int page, int size);

    /**
     * 회원 후원 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MySupportVO>
     */
    public List<MySupportVO> getUserSupportList(long userId, int page, int size);

    /**
     * 회원 봉사 활동 내역 조회 메서드
     * @author 정은찬
     * @param userId
     * @param page
     * @param size
     * @return List<MyVolunteerVO>
     */
    public List<MyVolunteerVO> getUserVolunteerList(long userId, int page, int size);

    /**
     * 로그인 아이디 존재 여부 확인 메서드
     * @author 정은찬
     * @param loginIdDTO
     * @return Boolean
     */
    public Boolean checkExistLoginId(LoginIdDTO loginIdDTO);

    /**
     * 마이페이지 회원 정보 조회 메서드
     * @author 정은찬
     * @param userId
     * @return MyPageDTO
     */
    public MyPageDTO getMyPageInfo(long userId);
}
