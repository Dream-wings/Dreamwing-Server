package com.sbsj.dreamwing.volunteer.dto;


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
public class VolunteerDetailDTO {

    private long volunteerId;
    private String title;
    private String content;
    private String type;
    private String category;
    private String volunteerStartDate;
    private String volunteerEndDate;
    private String address;
    private int totalCount;
    private int status;
    private String RecruitStartDate;
    private String RecruitEndDate;
}
