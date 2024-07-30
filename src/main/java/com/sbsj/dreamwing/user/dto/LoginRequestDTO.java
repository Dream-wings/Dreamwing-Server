package com.sbsj.dreamwing.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 로그인 정보를 담는 DTO 클래스
 * @author 정은찬
 * @since 2024.07.29
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.07.29       정은찬                      최초 생성
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String loginId;
    private String password;
}
