package com.sbsj.dreamwing.volunteer.dto;


import lombok.Data;

import java.util.Date;

/**
 * 봉사 공고 상세페이지 조회 DTO
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * </pre>
 */
@Data
public class VolunteerDetailDTO {

    private long volunteerId;
    private String title;
    private String content;
    private int type;
    private int category;
    private Date volunteerStartDate;
    private Date volunteerEndDate;
    private String address;
    private int totalCount;
    private int status;
    private Date recruitStartDate;
    private Date recruitEndDate;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private int currentApplicantCount;
}
