package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.util.S3Uploader;
import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
 * 2025.08.03   정은지        봉사활동 인증 메서드 추가
 * </pre>
 */


@Service
@AllArgsConstructor
public class VolunteerServiceImpl implements VolunteerService{

    private final VolunteerMapper volunteerMapper;
    private final S3Uploader s3Uploader;


@Override
public List<VolunteerListDTO> getVolunteerListWithFilters(int page, int size, int status, Integer type) {
    int offset = page * size;
    // Delegate filtering to the mapper
    return volunteerMapper.getVolunteerListWithFilters(offset, size, status, type);
}


    @Override
    public VolunteerDetailDTO getVolunteerDetail(long volunteerId) throws Exception{
        return volunteerMapper.getVolunteerDetail(volunteerId);
    }

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

//    @Override
//    public boolean cancelVolunteerApplication(PostApplyVolunteerRequestDTO request) {
//        // 봉사 신청 취소
//        int result = volunteerMapper.deleteVolunteerApplication(request);
//        return result > 0;
//    }

    // New method to check if a user has applied
    @Override
    public boolean hasUserApplied(long volunteerId, long userId) {
        return volunteerMapper.existsByVolunteerIdAndUserId(volunteerId, userId);
    }

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


    @Override
    public boolean certificationVolunteer(
            CertificationVolunteerRequestDTO certificationVolunteerRequestDTO, MultipartFile imageFile) throws Exception {

        String imageUrl = "";

        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3Uploader.uploadFile(imageFile);
        }

        CertificationVolunteerRequestDTO request =
                CertificationVolunteerRequestDTO.builder()
                        .userId(certificationVolunteerRequestDTO.getUserId())
                        .volunteerId(certificationVolunteerRequestDTO.getVolunteerId())
                        .imageUrl(imageUrl)
                        .build();

        return volunteerMapper.updateImageUserVolunteer(request) == 1;
    }

    @Override
    public int getApplicationStatus(long volunteerId, long userId) {
        return volunteerMapper.getApplicationStatus(volunteerId, userId);
    }
}




