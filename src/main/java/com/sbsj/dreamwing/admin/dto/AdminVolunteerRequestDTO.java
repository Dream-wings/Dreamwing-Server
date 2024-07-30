package com.sbsj.dreamwing.admin.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class AdminVolunteerRequestDTO {
    private long volunteerId;       // 봉사 ID (수정 시 필요)
    private String title;           // 봉사 제목
    private String content;         // 봉사 내용
    private int type;               // 봉사 또는 멘토링
    private int category;           // 카테고리

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;    // 봉사 시작일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;      // 봉사 종료일

    private String address;         // 봉사 주소
    private int totalCount;         // 모집 인원 수
    private int status;             // 봉사 상태

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recruitStartDate; // 모집 시작일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recruitEndDate;   // 모집 종료일

    private String imageUrl;        // 이미지 URL
    private String latitude;        // 위도
    private String longitude;       // 경도
}