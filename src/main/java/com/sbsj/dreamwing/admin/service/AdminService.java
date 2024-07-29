package com.sbsj.dreamwing.admin.service;


import com.sbsj.dreamwing.admin.dto.AwardVolunteerPointsRequestDTO;
import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;

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
 * </pre>
 */
public interface AdminService {
    boolean approveVolunteerRequest(UpdateVolunteerStatusRequestDTO request) throws Exception;
    boolean awardVolunteerPoints(AwardVolunteerPointsRequestDTO request) throws Exception;
}