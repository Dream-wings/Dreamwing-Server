package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.AwardVolunteerPointsRequestDTO;
import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 관리자 서비스 테스트
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 신청 승인 테스트 추가
 * 2024.07.29   정은지        봉사활동 인증 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    private AdminService service;

    @Test
    @DisplayName("봉사활동 신청 승인 서비스 테스트")
    public void testApproveVolunteerRequest() throws Exception {
        // given
        UpdateVolunteerStatusRequestDTO dto = new UpdateVolunteerStatusRequestDTO();
        dto.setVolunteerId(3L);
        dto.setUserId(1L);

        // when
        boolean success = service.approveVolunteerRequest(dto);

        // then
        assertTrue(success, "봉사활동 신청 승인 실패");
    }

    @Test
    @DisplayName("봉사활동 인증 포인트 부여 서비스 테스트")
    public void testVerifyVolunteerRequest() throws Exception {
        // given
        AwardVolunteerPointsRequestDTO dto = new AwardVolunteerPointsRequestDTO();
        dto.setVolunteerId(3L);
        dto.setUserId(1L);
        dto.setActivityType(1);
        dto.setActivityTitle("봉사활동");
        dto.setPoint(5000);

        // when
        boolean success = service.awardVolunteerPoints(dto);

        // then
        assertTrue(success, "봉사활동 인증 실패");
    }
}
