package com.sbsj.dreamwing.support.dto;


import lombok.Data;


import java.util.Date;

/**
 * 후원 상세 정보 응답 DTO
 *
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.04   임재성        최초 생성
 */
@Data
public class GetDetailSupportResponseDTO {
    private Long supportId;
    private int goalPoint;
    private int currentPoint;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
}
