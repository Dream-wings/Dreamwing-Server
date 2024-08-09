package com.sbsj.dreamwing.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 회원 회원가입 정보를 담는 DTO 클래스
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.07.28       정은찬                      최초 생성
 * 2024.07.31       정은찬                MultipartFile 변수 생성
 * </pre>
 */
@Data
public class SignUpRequestDTO {
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private MultipartFile imageFile;
    private String profileImageUrl;
}
