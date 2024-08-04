package com.sbsj.dreamwing.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 마이페이지 사용자 정보를 담는 DTO 클래스
 * @author 정은찬
 * @since 2024.07.31
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.08.03       정은찬                      최초 생성
 * </pre>
 */
@Data
@Builder
public class UserInfoDTO {
    String name;
    String phone;
    String profileImageUrl;
}