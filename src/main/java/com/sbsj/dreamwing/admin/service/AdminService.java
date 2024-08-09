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
 * 2024.07.28   정은지        봉사활동 신청 승인 메서드 추가
 * 2024.07.29   정은지        봉사활동 인증 후 포인트 지급 메서드 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 조회 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 생성/수정/삭제 기능 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 목록, 상세 메서드 추가
 * 2024.08.05   정은지        봉사활동 인증 대기 목록, 상세 메서드 추가
 * </pre>
 */
public interface AdminService {

    /**
     * 봉사활동 신청 승인
     * @author 정은지
     * @param updateVolunteerStatusRequestDTO
     * @return boolean
     * @throws Exception
     */
    boolean approveVolunteerRequest(UpdateVolunteerStatusRequestDTO updateVolunteerStatusRequestDTO) throws Exception;

    /**
     * 봉사활동 인증 및 포인트 지급
     * @author 정은지
     * @param awardVolunteerPointsRequestDTO
     * @return boolean
     * @throws Exception
     */
    boolean awardVolunteerPoints(AwardVolunteerPointsRequestDTO awardVolunteerPointsRequestDTO) throws Exception;

    /**
     * 봉사활동 생성
     * @author 임재성
     * @param request
     * @return int
     */
    int createVolunteer(AdminVolunteerRequestDTO request);

//    AdminVolunteerRequestDTO getVolunteerDetails(long volunteerId);

    /**
     * 봉사활동 수정
     * @author
     * @param id
     * @param request
     * @return int
     */
    int updateVolunteer(long id, AdminVolunteerRequestDTO request);

    /**
     * 봉사활동 삭제
     * @author 임재성
     * @param volunteerId
     * @return int
     */
    int deleteVolunteer(long volunteerId);

//    List<AdminVolunteerResponseDTO> getVolunteerList();

    /**
     * 봉사활동 리스트 조회
     * @author 임재성
     * @param page
     * @param size
     * @return
     */
    List<AdminVolunteerResponseDTO> getVolunteerListWithPaging(int page, int size);  // 새로운 메소드 추가

    /**
     * 봉사활동 신청 대기 목록 조회
     * @author 정은지
     * @param page
     * @param size
     * @return List<VolunteerRequestListResponseDTO>
     * @throws Exception
     */
    List<VolunteerRequestListResponseDTO> getVolunteerRequestPendingList(int page, int size) throws Exception;

    /**
     * 봉사활동 신청 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return VolunteerRequestDetailResponseDTO
     * @throws Exception
     */
    VolunteerRequestDetailResponseDTO getVolunteerRequestPendingDetail(long volunteerId, long userId) throws Exception;

    /**
     * 봉사활동 인증 대기 목록 조회
     * @author 정은지
     * @param page
     * @param size
     * @return List<VolunteerRequestListResponseDTO>
     * @throws Exception
     */
    List<VolunteerRequestListResponseDTO> getVolunteerCertificationList(int page, int size) throws Exception;

    /**
     * 봉사활동 인증 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return VolunteerCertificationDetailResponseDTO
     * @throws Exception
     */
    VolunteerCertificationDetailResponseDTO getVolunteerCertificationDetail(long volunteerId, long userId) throws Exception;

}