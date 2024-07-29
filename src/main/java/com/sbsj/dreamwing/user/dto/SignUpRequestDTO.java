package com.sbsj.dreamwing.user.dto;

import lombok.Data;

/**
 * 사용자 회원가입 정보를 담는 DTO 클래스
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.07.28       정은찬                      최초 생성
 * </pre>
 */
@Data
public class SignUpRequestDTO {
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String profileImageUrl;
}
