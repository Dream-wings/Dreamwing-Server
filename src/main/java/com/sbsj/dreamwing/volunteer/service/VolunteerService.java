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
 * 2024.07.30   임재성        봉사 신청 메서드 추가
 * 2024.08.03   정은지        봉사활동 인증 메서드 추가
 * </pre>
 */





public interface VolunteerService {
//    List<VolunteerListDTO> getVolunteerList() throws Exception;
    List<VolunteerListDTO> getVolunteerList(int page, int size);
    VolunteerDetailDTO getVolunteerDetail(long volunteerId) throws Exception;

    boolean applyVolunteer(PostApplyVolunteerRequestDTO request);

    //boolean cancelVolunteerApplication(PostApplyVolunteerRequestDTO request);
    boolean hasUserApplied(long volunteerId, long userId);
    boolean cancelApplication(PostApplyVolunteerRequestDTO request);

    boolean certificationVolunteer(CertificationVolunteerRequestDTO request, MultipartFile imageFile) throws Exception;

    int getApplicationStatus(long volunteerId, long userId);


}
