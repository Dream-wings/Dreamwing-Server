package com.sbsj.dreamwing.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 봉사활동 신청 대기 리스트 조회 응답 DTO
 * @author 정은지
 * @since 2024.08.04
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.04  	정은지        최초 생성
 * </pre>
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRequestListResponseDTO {
    private long volunteerId;
    private long userId;
    private int type;
    private String title;
    private String loginId;
}
