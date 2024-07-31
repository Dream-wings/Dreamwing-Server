package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.domain.UserVO;
import com.sbsj.dreamwing.user.dto.LoginRequestDTO;
import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;
import com.sbsj.dreamwing.user.dto.UserDTO;
import com.sbsj.dreamwing.user.dto.UserUpdateDTO;

/**
 * 사용자 관련 서비스 interface
 * @apiNote 회원 정보 저장 등의 기능을 제공
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
 * </pre>
 */
public interface UserService {
    public String signUp(SignUpRequestDTO signUpRequestDTO);
    public String login(LoginRequestDTO loginRequestDTO) throws Exception;
    public boolean withdraw(long userId);
    public UserDTO getUserInfo(long userId);
    public boolean updateUserInfo(UserUpdateDTO userUpdateDTO);
    public void logout(long userId);
}
