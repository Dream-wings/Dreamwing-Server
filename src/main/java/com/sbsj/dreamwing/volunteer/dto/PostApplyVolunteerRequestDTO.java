package com.sbsj.dreamwing.volunteer.dto;

import lombok.Data;

/**
 * 봉사 신청 DTO
 * @author 임재성
 * @since 2024.07.29
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.30  	임재성        최초 생성
 * </pre>
 */
@Data
public class PostApplyVolunteerRequestDTO {

    private long volunteerId;   // 봉사 ID
    private long userId;        // 사용자 ID
}