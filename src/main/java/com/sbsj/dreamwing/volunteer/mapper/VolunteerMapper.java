package com.sbsj.dreamwing.volunteer.mapper;


import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 봉사 매퍼 인터페이스
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * 2024.07.28   임재성        봉사 모집공고 리스트 & 상세페이지 조회 메서드 추가
 * 2024.08.03   정은지        봉사활동 인증 메서드 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 메서드 추가
 * 2024.08.04   임재성        봉사활동 필터 메서드 추가
 * 2024.08.06   임재성        봉사활동 신청 승인 상태 확인 메서드 추가
 * </pre>
 */

@Mapper
public interface VolunteerMapper {

    /**
     * 봉사 리스트 조회
     * @author 임재성
     * @param offset
     * @param size
     * @param status
     * @param type
     * @return List<VolunteerListDTO>
     */
    List<VolunteerListDTO> getVolunteerListWithFilters(@Param("offset") int offset, @Param("size") int size, @Param("status") int status, @Param("type") Integer type);

    /**
     * 봉사 상세 페이지 조회
     * @author 임재성
     * @param volunteerId
     * @return VolunteerDetailDTO
     */
    VolunteerDetailDTO getVolunteerDetail(long volunteerId);

    /**
     * 봉사활동 신청
     * @author 임재성
     * @param request
     * @return int
     */
    int insertVolunteerApplication(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사 신청 여부 확인
     * @author 임재성
     * @param request
     * @return int
     */
    int checkIfAlreadyApplied(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사활동 신청 취소
     * @author 임재성
     * @param request
     * @return int
     */
    int deleteVolunteerApplication(PostApplyVolunteerRequestDTO request);


    /**
     * 봉사 신청 여부 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return boolean
     */
    boolean  existsByVolunteerIdAndUserId(@Param("volunteerId") long volunteerId, @Param("userId") long userId);

    /**
     * 봉사활동 신청 취소
     * @author 임재성
     * @param request
     * @return int
     */
    int deleteApplication(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사 신청 여부 확인
     * @author 임재성
     * @param request
     * @return int
     */
    int checkIfApplicationExists(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사활동 인증 이미지 추가
     * @author 정은지
     * @param request
     * @return int
     */
    int updateImageUserVolunteer(CertificationVolunteerRequestDTO request);

    /**
     * 봉사활동 신청 승인 상태 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return int
     */
    int getApplicationStatus(@Param("volunteerId") long volunteerId, @Param("userId") long userId);

}


