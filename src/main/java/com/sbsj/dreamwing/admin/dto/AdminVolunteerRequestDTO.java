package com.sbsj.dreamwing.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;


/**
 * 관리자 봉사활동 생성,수정 요청 DTO
 * @author 임재성
 * @since 2024.07.30
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.30  	임재성        최초 생성
 * </pre>
 */
@Data
public class AdminVolunteerRequestDTO {
    private long volunteerId; // 봉사 ID (수정 시 필요)
    private String title; // 봉사 제목
    private String content; // 봉사 내용
    private int type; // 봉사 또는 멘토링
    private int category; // 카테고리

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime volunteerStartDate; // 봉사 시작일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime volunteerEndDate; // 봉사 종료일

    private String address; // 봉사 주소
    private int totalCount; // 모집 인원 수
    private int status; // 봉사 상태

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recruitStartDate; // 모집 시작일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recruitEndDate; // 모집 종료일

    private String imageUrl; // 이미지 URL
    private double latitude; // 위도
    private double longitude; // 경도
    private MultipartFile imageFile; // 이미지 파일
}
