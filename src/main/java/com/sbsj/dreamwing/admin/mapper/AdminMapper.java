package com.sbsj.dreamwing.admin.mapper;

import com.sbsj.dreamwing.admin.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 관리자 매퍼 인터페이스
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 신청 승인 메서드 추가
 * 2024.07.29   정은지        봉사활동 인증 메서드 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 조회 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 생성/수정/삭제 기능 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 리스트 조회, 상세 조회 메서드 추가
 * 2024.08.05   정은지        봉사활동 인증 대기 리스트 조회, 상세 조회 메서드 추가
 * </pre>
 */
@Mapper
public interface AdminMapper {

    /**
     * 봉사활동 신청 승인
     * @author 정은지
     * @param updateVolunteerStatusRequestDTO
     * @return int
     */
    int updateVolunteerStatus(UpdateVolunteerStatusRequestDTO updateVolunteerStatusRequestDTO);

    /**
     * 봉사활동 인증
     * @author 정은지
     * @param updateVolunteerStatusRequestDTO
     * @return int
     */
    int updateVolunteerVerified(UpdateVolunteerStatusRequestDTO updateVolunteerStatusRequestDTO);

    /**
     * 봉사활동 생성
     * @author 임재성
     * @param request
     * @return int
     */
    int insertVolunteer(AdminVolunteerRequestDTO request);

    /**
     * 봉사활동 수정
     * author 임재성
     * @param request
     * @return int
     */
    int updateVolunteer(AdminVolunteerRequestDTO request);

    /**
     * 봉사활동 삭제
     * @author 임재성
     * @param volunteerId
     * @return int
     */
    int deleteVolunteer(long volunteerId);

    /**
     * 봉사활동 리스트 조회
     * @author 임재성
     * @return List
     */
    List<AdminVolunteerResponseDTO> selectVolunteerList();

//    AdminVolunteerRequestDTO selectVolunteerById(long volunteerId);

    /**
     * @author 정은지
     * 봉사활동 대기 목록 조회
     * @param offset
     * @param size
     * @return List<VolunteerRequestListResponseDTO>
     */
    List<VolunteerRequestListResponseDTO> selectVolunteerRequestPendingList(@Param("offset") int offset, @Param("size") int size);

    /**
     * 봉사활동 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return VolunteerRequestDetailResponseDTO
     */
    VolunteerRequestDetailResponseDTO selectVolunteerRequestPendingDetail(
            @Param("volunteerId") long volunteerId, @Param("userId") long userId);

    /**
     * 봉사활동 인증 대기 목록 조회
     * @author 정은지
     * @param offset
     * @param size
     * @return List<VolunteerRequestListResponseDTO>
     */
    List<VolunteerRequestListResponseDTO> selectVolunteerCertificationList(@Param("offset") int offset, @Param("size") int size);

    /**
     * 봉사활동 인증 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return VolunteerCertificationDetailResponseDTO
     */
    VolunteerCertificationDetailResponseDTO selectVolunteerCertificationDetail(
            @Param("volunteerId") long volunteerId, @Param("userId") long userId);

    /**
     * 봉사활동 리스트 조회 (페이징)
     * @author 임재성
     * @param offset
     * @param size
     * @return List
     */
    List<AdminVolunteerResponseDTO> selectVolunteerListWithPaging(@Param("offset") int offset, @Param("size") int size);

}