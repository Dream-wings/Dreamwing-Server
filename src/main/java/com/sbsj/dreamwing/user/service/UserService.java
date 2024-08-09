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
 * ----------  ----------------             ---------------------------------
 *  2024.07.28     	정은찬        		       최초 생성 및 회원가입 기능 추가
 *  2024.07.29      정은찬                      로그인 기능 추가
 *  2024.07.31      정은찬                      회원탈퇴 기능 및 회원 정보 가져오기 기능 추가
 *  2024.07.31      정은찬                      회원 정보 업데이트 기능 및 로그아웃 기능 추가
 *  2024.07.31      정은찬                      포인트 내역 조회 기능 및 후원 내역 조회 기능 추가
 *  2024.07.31      정은찬                      회원가입 및 회원 정보 업데이트 프로필 이미지 S3 업로드 기능 추가
 *  2024.08.02      정은찬                      로그인 아이디 존재 여부 확인 기능 추가
 *  2024.08.03      정은찬                      마이페이지 회원 정보 조회 기능 추가
 *  2024.08.04      정은찬                      페이징 처리를 위해 포인트 내역, 후원 내역 조회 기능 수정
 *  2024.08.05      정은찬                      회원 정보 업데이트 기능 수정, 봉사 활동 내역 조회 기능 추가
 * </pre>
 */
public interface UserService {
    public String signUp(SignUpRequestDTO signUpRequestDTO);    // 회원가입 기능 
    public String login(LoginRequestDTO loginRequestDTO) throws Exception;  // 로그인 기능
    public boolean withdraw(long userId);   // 회원탈퇴 기능
    public UserInfoDTO getUserInfo(long userId);    // 회원 정보 가져오기 기능
    public boolean updateUserInfo(long userId, UserUpdateDTO userUpdateDTO);    // 회원 정보 업데이트 기능
    public void logout(long userId);    // 로그아웃 기능 구현
    public List<MyPointVO> getUserPointList(long userId, int page, int size);   // 회원 포인트 내역 조회
    public List<MySupportVO> getUserSupportList(long userId, int page, int size);   // 회원 후원 내역 조회
    public List<MyVolunteerVO> getUserVolunteerList(long userId, int page, int size);   // 회원 봉사활동 내역 조회
    public Boolean checkExistLoginId(LoginIdDTO loginIdDTO);    // 로그인 이아디 존재 여부 확인 기능
    public MyPageDTO getMyPageInfo(long userId);    // 마이페이지 회원 정보 조회
}
