package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;

/**
 * 사용자 관련 서비스 interface
 * @apiNote 회원 정보 저장 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.28     	정은찬        		        최초 생성
 * </pre>
 */
public interface UserService {
    public boolean signUp(SignUpRequestDTO signUpRequestDTO);
}
