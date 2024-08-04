package com.sbsj.dreamwing.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 봉사활동 인증 대기 상세 조회 응답 DTO
 * @author 정은지
 * @since 2024.08.05
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.05  	정은지        최초 생성
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerCertificationDetailResponseDTO {
    private long volunteerId;
    private long userId;
    private int type;
    private String title;
    private String loginId;
    private String imageUrl;
}