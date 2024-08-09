package com.sbsj.dreamwing.mission.dto;

import lombok.*;

/**
 * 포인트 부여 요청 DTO
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
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AwardPointsRequestDTO {
    private long userId;
    private int activityType;
    private String activityTitle;
    private int point;
}
