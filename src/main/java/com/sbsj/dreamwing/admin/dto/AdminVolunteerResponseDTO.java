package com.sbsj.dreamwing.admin.dto;

import lombok.Data;

@Data
public class AdminVolunteerResponseDTO {
    private long volunteerId;
    private int type;
    private String title;
    private int currentCount;
    private int totalCount;               // 현재 인원 수 (실제 컬럼이 아니며 쿼리로 계산)
}
