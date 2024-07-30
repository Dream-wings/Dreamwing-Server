package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class VolunteerServiceTests {

    @Autowired
    private VolunteerService service;

    @Test
    @DisplayName("모집공고 조회 테스트")
    public void testGetVolunteerList() throws Exception {
        List<VolunteerListDTO> volunteerDTO = service.getVolunteerList();
        log.info(String.valueOf(volunteerDTO));
    }

    @Test
    @DisplayName("모집공고 상세페이지 조회 테스트")
    public void testGetVolunteerDetail() throws Exception {
        long volunteerId = 4L;
        VolunteerDetailDTO volunteerDTO = service.getVolunteerDetail(volunteerId);
        log.info(String.valueOf(volunteerDTO));
    }

    @Test
    @DisplayName("봉사 신청 테스트")
    public void testApplyVolunteer_Success() throws Exception {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(3L);
        request.setUserId(2L);

        // Pre-condition: Ensure the user has not already applied
        boolean success = service.applyVolunteer(request);

        // then
        assertTrue(success, "봉사 신청 실패");
    }

    @Test
    @DisplayName("봉사 신청 중복 체크 테스트")
    public void testApplyVolunteer_AlreadyApplied() throws Exception {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(1L);
        request.setUserId(3L);

        // Pre-condition: Ensure the user has already applied
        service.applyVolunteer(request);

        // when
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            service.applyVolunteer(request);
        });

        // then
        assertTrue(thrown.getMessage().contains("이미 신청한 봉사입니다."), "예외 메시지 불일치");
    }

    @Test
    @DisplayName("봉사 취소 테스트")
    public void testCancelVolunteerApplication() throws Exception {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(1L); // 적절한 봉사 ID
        request.setUserId(2L); // 적절한 사용자 ID

        // when: 봉사 취소 시도
        boolean cancelSuccess = service.cancelVolunteerApplication(request);

        // then: 봉사 취소가 성공해야 함
        assertTrue(cancelSuccess, "봉사 취소 성공");

    }


}



