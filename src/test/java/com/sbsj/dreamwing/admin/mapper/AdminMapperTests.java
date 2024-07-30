package com.sbsj.dreamwing.admin.mapper;

import com.sbsj.dreamwing.admin.dto.AdminVolunteerRequestDTO;
import com.sbsj.dreamwing.admin.dto.AdminVolunteerResponseDTO;
import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

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


    @Test
    @DisplayName("봉사 공고 생성 매퍼 테스트")
    public void testInsertVolunteer() {
        // given
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setTitle("테스트 봉사 공고");
        dto.setContent("테스트 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setStartDate(Timestamp.valueOf("2024-08-01 09:00:00"));
        dto.setEndDate(Timestamp.valueOf("2024-08-01 12:00:00"));
        dto.setAddress("테스트 주소");
        dto.setTotalCount(20);
        dto.setStatus(0);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
        dto.setImageUrl("https://example.com/image.jpg");
        dto.setLatitude("37.5665");
        dto.setLongitude("126.978");

        // when
        int success = mapper.insertVolunteer(dto);

        // then
        Assertions.assertEquals(1, success);
    }

    @Test
    @DisplayName("봉사 공고 수정 매퍼 테스트")
    public void testUpdateVolunteer() {
        // given
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setVolunteerId(1L); // Assuming this ID exists
        dto.setTitle("수정된 봉사 공고");
        dto.setContent("수정된 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setStartDate(Timestamp.valueOf("2024-08-01 09:00:00"));
        dto.setEndDate(Timestamp.valueOf("2024-08-01 12:00:00"));
        dto.setAddress("수정된 주소");
        dto.setTotalCount(30);
        dto.setStatus(1);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
        dto.setImageUrl("https://example.com/new_image.jpg");
        dto.setLatitude("37.567");
        dto.setLongitude("126.979");

        // when
        int success = mapper.updateVolunteer(dto);

        // then
        Assertions.assertEquals(1, success);
    }

    @Test
    @DisplayName("봉사 공고 삭제 매퍼 테스트")
    public void testDeleteVolunteer() {
        // given
        long volunteerId = 7L; // Assuming this ID exists

        // when
        int success = mapper.deleteVolunteer(volunteerId);

        // then
        Assertions.assertEquals(1, success);
    }

    @Test
    @DisplayName("봉사 공고 목록 조회 매퍼 테스트")
    public void testSelectVolunteerList() {
        // when
        List<AdminVolunteerResponseDTO> list = mapper.selectVolunteerList();

        // then
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }
}
