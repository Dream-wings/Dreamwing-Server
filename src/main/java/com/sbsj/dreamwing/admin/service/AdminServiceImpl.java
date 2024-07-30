package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.AdminVolunteerRequestDTO;
import com.sbsj.dreamwing.admin.dto.AdminVolunteerResponseDTO;
import com.sbsj.dreamwing.admin.dto.AwardVolunteerPointsRequestDTO;
import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.admin.mapper.AdminMapper;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.mapper.MissionMapper;
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
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper mapper;
    private final MissionMapper missionMapper;

    @Override
    public boolean approveVolunteerRequest(UpdateVolunteerStatusRequestDTO request) {
        return mapper.updateVolunteerStatus(request) == 1;
    }

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

    @Override
    @Transactional
    public int createVolunteer(AdminVolunteerRequestDTO request) {
        return mapper.insertVolunteer(request);
    }

    @Override
    @Transactional
    public int updateVolunteer(AdminVolunteerRequestDTO request) {
        return mapper.updateVolunteer(request);
    }

    @Override
    @Transactional
    public int deleteVolunteer(long volunteerId) {
        return mapper.deleteVolunteer(volunteerId);
    }

    @Override
    public List<AdminVolunteerResponseDTO> getVolunteerList() {
        return mapper.selectVolunteerList();
    }
}
