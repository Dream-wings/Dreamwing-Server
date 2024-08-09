package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 봉사 서비스 테스트 클래스
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26   임재성        최초생성
 * 2024.07.28   임재성        봉사 모집공고 게시판 리스트 & 상세 페이지 조회 기능 서비스 테스트 추가
 * 2024.07.31   임재성        봉사 & 멘토링 리스트 페이징 처리 기능 서비스 테스트 추가
 * 2024.08.03   정은지        봉사활동 인증 서비스 테스트 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 기능 서비스 테스트 추가
 * 2024.08.04   임재성        봉사활동 필터 기능 서비스 테스트 추가
 * </pre>
 */
@Slf4j
@SpringBootTest
public class VolunteerServiceTests {

    @Autowired
    private VolunteerService service;

    @Test
    @DisplayName("봉사 모집공고 게시판 리스트 조회 테스트")
    public void testGetVolunteerListWithFilters() throws Exception {
        int page = 0;
        int size = 10;
        int status = 0;
        Integer type = 0;
        List<VolunteerListDTO> volunteerDTO = service.getVolunteerListWithFilters(page,size,status,type);
        log.info(String.valueOf(volunteerDTO));
    }

    @Test
    @DisplayName("봉사 모집공고 상세 페이지 조회 테스트")
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
    @DisplayName("봉사 신청 상태 여부 테스트")
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
    @DisplayName("봉사 신청 취소 테스트")
    public void testCancelVolunteerApplication() throws Exception {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(1L); // 적절한 봉사 ID
        request.setUserId(2L); // 적절한 사용자 ID

        // when: 봉사 취소 시도
        boolean cancelSuccess = service.cancelApplication(request);

        // then: 봉사 취소가 성공해야 함
        assertTrue(cancelSuccess, "봉사 취소 성공");

    }

    @Test
    @DisplayName("봉사활동 인증 서비스 테스트")
    public void testCertificationVolunteer() throws Exception {

    }
}



