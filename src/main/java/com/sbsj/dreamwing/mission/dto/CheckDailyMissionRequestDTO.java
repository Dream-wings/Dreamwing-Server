package com.sbsj.dreamwing.mission.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDailyMissionRequestDTO {
    private long userId;
    private int activityType;
    private String activityTitle;
}
