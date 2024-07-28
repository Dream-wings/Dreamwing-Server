package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import lombok.extern.slf4j.Slf4j;
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
}
