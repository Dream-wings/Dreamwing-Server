package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 봉사 서비스 인터페이스
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * 2024.07.28   임재성        봉사 모집공고 리스트 & 상세페이지 조회 메서드 추가
 * 2024.07.31   임재성        봉사 신청 메서드 추가
 * 2024.08.03   정은지        봉사활동 인증 메서드 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 메서드 추가
 * 2024.08.04   임재성        봉사활동 필터 메서드 추가
 * 2024.08.06   임재성        봉사활동 신청 승인 상태 확인 메서드 추가
 * </pre>
 */





public interface VolunteerService {

    /**
     * 봉사 리스트 조회
     * @author 임재성
     * @param page
     * @param size
     * @param status
     * @param type
     * @return List<VolunteerListDTO>
     */
    List<VolunteerListDTO> getVolunteerListWithFilters(int page, int size, int status, Integer type);

    /**
     * 봉사 상세 페이지 조회
     * @author 임재성
     * @param volunteerId
     * @return VolunteerDetailDTO
     * @throws Exception
     */
    VolunteerDetailDTO getVolunteerDetail(long volunteerId) throws Exception;

    /**
     * 봉사활동 신청
     * @author 임재성
     * @param request
     * @return boolean
     */
    boolean applyVolunteer(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사 신청 여부 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return boolean
     */
    boolean hasUserApplied(long volunteerId, long userId);

    /**
     * 봉사활동 신청 취소
     * @author 임재성
     * @param request
     * @return boolean
     */
    boolean cancelApplication(PostApplyVolunteerRequestDTO request);

    /**
     * 봉사활동 인증
     * @author 정은지
     * @param request
     * @param imageFile
     * @return boolean
     * @throws Exception
     */
    boolean certificationVolunteer(CertificationVolunteerRequestDTO request, MultipartFile imageFile) throws Exception;

    /**
     * 봉사활동 신청 승인 상태 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return int
     */
    int getApplicationStatus(long volunteerId, long userId);



}
