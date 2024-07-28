package com.sbsj.dreamwing.mission.domain;

import lombok.Data;

import java.util.Date;

/**
 * 퀴즈 내역 VO
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
@Data
public class PointHistoryVO {

    private long pointId;
    private long userId;
    private int activityType;
    private String activityTitle;
    private int point;
    private Date createdDate;
}
