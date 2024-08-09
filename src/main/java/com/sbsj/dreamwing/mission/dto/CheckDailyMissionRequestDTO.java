package com.sbsj.dreamwing.mission.dto;

import lombok.*;

/**
 * 데일리 미션 완료 여부 체크 DTO
 * @author 정은지
 * @since 2024.07.27
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.27  	정은지        최초 생성
 * </pre>
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDailyMissionRequestDTO {
    private long userId;
    private int activityType;
    private String activityTitle;
}
