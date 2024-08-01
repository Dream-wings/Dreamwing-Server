package com.sbsj.dreamwing.volunteer.dto;

import lombok.Data;
import java.util.Date;

/**
 * 봉사 공고 리스트 조회 DTO
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	임재성        최초 생성
 * </pre>
 */
@Data
public class VolunteerListDTO {
    private long volunteerId;
    private String title;                         //봉사 or 멘토링 제목
    private int type;                             //봉사 or 멘토링
    private int category;                      //봉사 or 멘토링 종류
    private Date volunteerStartDate;            //봉사 or 멘토링 시작 날짜
    private Date volunteerEndDate;              //봉사 or 멘토링 끝 날짜
    private String address;                       //봉사 or 멘토링 장소
    private int status;                           //봉사 or 멘토링 모집상태
    private String imageUrl;                      //봉사 or 멘토링 메인이미지
}
