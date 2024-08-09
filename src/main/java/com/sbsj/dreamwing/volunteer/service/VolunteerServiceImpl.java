package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.user.dto.UserDTO;
import com.sbsj.dreamwing.util.S3Uploader;
import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 봉사 매퍼 인터페이스
 *
 * @author 임재성
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * 2024.07.28   임재성        봉사 모집공고 리스트 & 상세페이지 조회 메서드 추가
 * 2024.07.31   임재성        봉사 신청 메서드 추가
 * 2025.08.03   정은지        봉사활동 인증 메서드 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 메서드 추가
 * 2024.08.04   임재성        봉사활동 필터 메서드 추가
 * 2024.08.06   임재성        봉사활동 신청 승인 상태 확인 메서드 추가
 * </pre>
 * @since 2024.07.26
 */


@Service
@AllArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerMapper volunteerMapper;
    private final S3Uploader s3Uploader;

    /**
     * 봉사 리스트 조회
     * @author 임재성
     * @param page
     * @param size
     * @param status
     * @param type
     * @return VolunteerListDTO
     */
    @Override
    public List<VolunteerListDTO> getVolunteerListWithFilters(int page, int size, int status, Integer type) {
        int offset = page * size;
        // Delegate filtering to the mapper
        return volunteerMapper.getVolunteerListWithFilters(offset, size, status, type);
    }

    /**
     * 봉사 상세 페이지 조회
     * @author 임재성
     * @param volunteerId
     * @return VolunteerDetailDTO
     * @throws Exception
     */
    @Override
    public VolunteerDetailDTO getVolunteerDetail(long volunteerId) throws Exception {
        return volunteerMapper.getVolunteerDetail(volunteerId);
    }

    /**
     * 봉사활동 신청
     * @author 임재성
     * @param request
     * @return boolean
     */
    @Override
    @Transactional
    public boolean applyVolunteer(PostApplyVolunteerRequestDTO request) {
        // 이미 신청했는지 확인
        int count = volunteerMapper.checkIfAlreadyApplied(request);

        // 신청이 이미 되어있는 경우
        if (count > 0) {
            // 예외 대신 false 반환 (컨트롤러에서 적절히 처리)
            return false;
        }

        // 봉사 신청
        int result = volunteerMapper.insertVolunteerApplication(request);
        return result > 0;
    }

    /**
     * 봉사 신청 여부 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return boolean
     */
    @Override
    public boolean hasUserApplied(long volunteerId, long userId) {
        return volunteerMapper.existsByVolunteerIdAndUserId(volunteerId, userId);
    }

    /**
     * 봉사활동 신청 취소
     * @author 임재성
     * @param request
     * @return boolean
     */
    @Override
    @Transactional
    public boolean cancelApplication(PostApplyVolunteerRequestDTO request) {
        int exists = volunteerMapper.checkIfApplicationExists(request);
        if (exists == 1) {
            int result = volunteerMapper.deleteApplication(request);
            return result > 0; // return true if at least one row was affected
        } else {
            return false; // application does not exist
        }
    }

    /**
     * 봉사활동 인증
     * @author 정은지
     * @param certificationVolunteerRequestDTO
     * @param imageFile
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean certificationVolunteer(
            CertificationVolunteerRequestDTO certificationVolunteerRequestDTO, MultipartFile imageFile) throws Exception {

        String imageUrl = "";

        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3Uploader.uploadFile(imageFile);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        CertificationVolunteerRequestDTO request =
                CertificationVolunteerRequestDTO.builder()
                        .userId(userId)
                        .volunteerId(certificationVolunteerRequestDTO.getVolunteerId())
                        .imageUrl(imageUrl)
                        .build();

        return volunteerMapper.updateImageUserVolunteer(request) == 1;
    }

    /**
     * 봉사활동 신청 승인 상태 확인
     * @author 임재성
     * @param volunteerId
     * @param userId
     * @return int
     */
    @Override
    public int getApplicationStatus(long volunteerId, long userId) {
        return volunteerMapper.getApplicationStatus(volunteerId, userId);
    }
}




