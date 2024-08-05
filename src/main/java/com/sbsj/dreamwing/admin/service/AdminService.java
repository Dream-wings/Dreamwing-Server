package com.sbsj.dreamwing.admin.service;


import com.sbsj.dreamwing.admin.dto.*;

import java.util.List;

/**
 * 관리자 서비스 인터페이스
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 승인 기능 추가
 * 2024.07.29   정은지        봉사활동 인증 후 포인트 부여 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 조회 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 생성/수정/삭제 기능 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 목록, 상세 조회 추가
 * 2024.08.05   정은지        봉사활동 인증 대기 목록, 상세 조회 추가
 * </pre>
 */
public interface AdminService {
    boolean approveVolunteerRequest(UpdateVolunteerStatusRequestDTO request) throws Exception;

    boolean awardVolunteerPoints(AwardVolunteerPointsRequestDTO request) throws Exception;

    int createVolunteer(AdminVolunteerRequestDTO request);

    AdminVolunteerRequestDTO getVolunteerDetails(long volunteerId);

    int updateVolunteer(AdminVolunteerRequestDTO request);

    int deleteVolunteer(long volunteerId);

    List<AdminVolunteerResponseDTO> getVolunteerList();

    List<VolunteerRequestListResponseDTO> getVolunteerRequestPendingList(int page, int size) throws Exception;

    VolunteerRequestDetailResponseDTO getVolunteerRequestPendingDetail(long volunteerId, long userId) throws Exception;

    List<VolunteerRequestListResponseDTO> getVolunteerCertificationList(int page, int size) throws Exception;

    VolunteerCertificationDetailResponseDTO getVolunteerCertificationDetail(long volunteerId, long userId) throws Exception;

}