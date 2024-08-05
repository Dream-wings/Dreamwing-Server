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
 * 2024.07.28   정은지        봉사활동 승인 기능 추가
 * 2024.07.29   정은지        봉사활동 인증 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 조회 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 생성/수정/삭제 기능 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 리스트 조회, 상세 조회 추가
 * 2024.08.05   정은지        봉사활동 인증 대기 리스트 조회, 상세 조회 추가
 * </pre>
 */
@Mapper
public interface AdminMapper {
    int updateVolunteerStatus(UpdateVolunteerStatusRequestDTO request);

    int updateVolunteerVerified(UpdateVolunteerStatusRequestDTO request);

    int insertVolunteer(AdminVolunteerRequestDTO request);

    int updateVolunteer(AdminVolunteerRequestDTO request);

    int deleteVolunteer(long volunteerId);

    List<AdminVolunteerResponseDTO> selectVolunteerList();

    AdminVolunteerRequestDTO selectVolunteerById(long volunteerId);

    List<VolunteerRequestListResponseDTO> selectVolunteerRequestPendingList(@Param("offset") int offset, @Param("size") int size);

    VolunteerRequestDetailResponseDTO selectVolunteerRequestPendingDetail(
            @Param("volunteerId") long volunteerId, @Param("userId") long userId);

    List<VolunteerRequestListResponseDTO> selectVolunteerCertificationList(@Param("offset") int offset, @Param("size") int size);

    VolunteerCertificationDetailResponseDTO selectVolunteerCertificationDetail(
            @Param("volunteerId") long volunteerId, @Param("userId") long userId);

    List<AdminVolunteerResponseDTO> selectVolunteerListWithPaging(@Param("offset") int offset, @Param("size") int size);

}