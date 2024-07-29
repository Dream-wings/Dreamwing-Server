package com.sbsj.dreamwing.admin.mapper;

import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 관리자 매퍼 테스트 클래스
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
public class AdminMapperTests {

    @Autowired
    private AdminMapper mapper;

    @Test
    @DisplayName("봉사활동 신청 승인 매퍼 테스트")
    public void testUpdateVolunteerStatus() {

        // given
        UpdateVolunteerStatusRequestDTO dto = new UpdateVolunteerStatusRequestDTO();
        dto.setVolunteerId(2L);
        dto.setUserId(1L);

        // when
        int success = mapper.updateVolunteerStatus(dto);

        // then
        Assertions.assertEquals(success, 1);
    }

    @Test
    @DisplayName("봉사활동 인증 매퍼 테스트")
    public void testUpdateVolunteerVerified() {

        // given
        UpdateVolunteerStatusRequestDTO dto = new UpdateVolunteerStatusRequestDTO();
        dto.setVolunteerId(2L);
        dto.setUserId(1L);

        // when
        int success = mapper.updateVolunteerVerified(dto);

        // then
        Assertions.assertEquals(success, 1);
    }
}
