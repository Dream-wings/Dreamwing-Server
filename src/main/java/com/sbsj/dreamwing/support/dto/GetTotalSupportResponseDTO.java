package com.sbsj.dreamwing.support.dto;

import lombok.Data;

/**
 * 후원 총 횟수 및 금액 조회 DTO
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * </pre>
 */
@Data
public class GetTotalSupportResponseDTO {
    private int totalCount;
    private long totalPoints;
}
