package com.sbsj.dreamwing.admin.dto;

import lombok.Data;

@Data
public class AdminVolunteerResponseDTO {
    private long volunteerId;
    private String title;                         // 봉사 or 멘토링 제목
    private int type;                             // 봉사 or 멘토링
    private int currentPerson;                    // 현재 인원 수 (실제 컬럼이 아니며 쿼리로 계산)
}
