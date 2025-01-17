package com.sbsj.dreamwing.admin.dto;

import lombok.*;

/**
 * 봉사활동 승인 요청 DTO
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * </pre>
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVolunteerStatusRequestDTO {
    private long volunteerId;
    private long userId;
}
