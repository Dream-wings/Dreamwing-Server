package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.*;
import com.sbsj.dreamwing.admin.mapper.AdminMapper;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.mapper.MissionMapper;
import com.sbsj.dreamwing.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 관리자 서비스 구현체
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
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper mapper;
    private final MissionMapper missionMapper;
    private final S3Uploader s3Uploader;

    /**
     * @author 정은지
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public boolean approveVolunteerRequest(UpdateVolunteerStatusRequestDTO request) throws Exception {
        return mapper.updateVolunteerStatus(request) == 1;
    }

    /**
     * author 정은지
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public boolean awardVolunteerPoints(AwardVolunteerPointsRequestDTO request) throws Exception {
        try {
            mapper.updateVolunteerVerified(new UpdateVolunteerStatusRequestDTO(
                    request.getVolunteerId(), request.getUserId()));

            missionMapper.callAwardPointsProcedure(new AwardPointsRequestDTO(
                    request.getUserId(), request.getActivityType(), request.getActivityTitle(), request.getPoint()));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

//    @Override
//    @Transactional
//    public int createVolunteer(AdminVolunteerRequestDTO request) {
//        return mapper.insertVolunteer(request);
//    }
@Transactional
public int createVolunteer(AdminVolunteerRequestDTO request) {
    // 로그 추가
    log.info("createVolunteer 호출됨");
    log.info("요청 데이터: {}", request);

    // 이미지 파일이 있는 경우 업로드 처리
    if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
        log.info("이미지 파일이 존재합니다. 파일 이름: {}", request.getImageFile().getOriginalFilename());
        String imageUrl = s3Uploader.uploadFile(request.getImageFile());
        request.setImageUrl(imageUrl);
        log.info("이미지 업로드 완료. 업로드된 이미지 URL: {}", imageUrl);
    } else {
        log.info("이미지 파일이 존재하지 않거나 비어있습니다.");
    }

    // 데이터베이스에 봉사 공고 삽입
    int result = mapper.insertVolunteer(request);
    log.info("봉사 공고 데이터베이스에 삽입됨. 결과: 2024-08-05 15:03:15.157  9271-9472  okhttp.OkHttpClient     com.sbsj.dreamwing                   I  {\"address\":\"seorul\",\"category\":1,\"content\":\"content\",\"imageUrl\":\"content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F44/ORIGINAL/NONE/image%2Fjpeg/630300757\",\"latitude\":374.85315,\"longitude\":24677.35285,\"recruitEndDate\":\"2024-08-30T00:00:00\",\"recruitStartDate\":\"2024-08-10T00:00:00\",\"status\":0,\"title\":\"title\",\"totalCount\":20,\"type\":0,\"volunteerEndDate\":\"2024-09-22T00:00:00\",\"volunteerId\":0,\"volunteerStartDate\":\"2024-09-10T00:00:00\"}\n{}", result);

    return result;
}

    @Override
    public AdminVolunteerRequestDTO getVolunteerDetails(long volunteerId) {
        return mapper.selectVolunteerById(volunteerId);
    }

    @Override
    public int updateVolunteer(long id, AdminVolunteerRequestDTO request) {
        request.setVolunteerId(id);
        return mapper.updateVolunteer(request);
    }

    @Override
    public int deleteVolunteer(long volunteerId) {
        return mapper.deleteVolunteer(volunteerId);
    }

    @Override
    public List<AdminVolunteerResponseDTO> getVolunteerList() {
        return mapper.selectVolunteerList();
    }

    /**
     * 봉사활동 신청 대기 목록 조회
     * @author 정은지
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    public List<VolunteerRequestListResponseDTO> getVolunteerRequestPendingList(int page, int size) throws Exception {
        int offset = page * size;
        return mapper.selectVolunteerRequestPendingList(offset, size);
    }

    /**
     * 봉사활동 신청 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public VolunteerRequestDetailResponseDTO getVolunteerRequestPendingDetail(long volunteerId, long userId) throws Exception {
        return mapper.selectVolunteerRequestPendingDetail(volunteerId, userId);
    }

    /**
     * 봉사활동 인증 대기 목록 조회
     * @author 정은지
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    public List<VolunteerRequestListResponseDTO> getVolunteerCertificationList(int page, int size) throws Exception {
        int offset = page * size;
        return mapper.selectVolunteerCertificationList(offset, size);
    }


    /**
     * 봉사활동 인증 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public VolunteerCertificationDetailResponseDTO getVolunteerCertificationDetail(long volunteerId, long userId) throws Exception {
        return mapper.selectVolunteerCertificationDetail(volunteerId, userId);
    }

    @Override
    public List<AdminVolunteerResponseDTO> getVolunteerListWithPaging(int page, int size) {
        int offset = page * size;  // 페이징을 위한 오프셋 계산
        return mapper.selectVolunteerListWithPaging(offset, size);
    }
}
