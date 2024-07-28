package com.sbsj.dreamwing.support.domain;

import lombok.Data;

import java.util.Date;

/**
 * 후원 VO
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
public class SupportVO {
    private long supportId;
    private int goalPoint;
    private int currentPoint;
    private int category;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
}
