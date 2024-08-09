package com.sbsj.dreamwing.admin.dto;

import lombok.Data;


/**
 * 관리자 봉사활동 리스트 조회 요청 DTO
 * @author 임재성
 * @since 2024.07.30
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.30  	임재성        최초 생성
 * </pre>
 */
@Data
public class AdminVolunteerResponseDTO {
    private long volunteerId;
    private int type;
    private String title;
    private int currentCount;
    private int totalCount;               // 현재 인원 수 (실제 컬럼이 아니며 쿼리로 계산)
}
