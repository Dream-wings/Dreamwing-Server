package com.sbsj.dreamwing.support.dto;

import lombok.*;

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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTotalSupportResponseDTO {
    private int totalCount;
    private long totalPoints;
}
