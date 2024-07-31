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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(success, 1);
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
        assertEquals(success, 1);
    }


    @Test
    public void testInsertVolunteer() {
        AdminVolunteerRequestDTO request = new AdminVolunteerRequestDTO();
        request.setTitle("환경 보호 봉사");
        request.setContent("환경 정화를 위한 봉사활동입니다.");
        request.setType(1);
        request.setCategory(2);
        request.setStartDate(LocalDateTime.of(2024, 8, 1, 9, 0));
        request.setEndDate(LocalDateTime.of(2024, 8, 1, 18, 0));
        request.setAddress("서울시 강남구");
        request.setTotalCount(100);
        request.setStatus(1);
        request.setRecruitStartDate(LocalDateTime.of(2024, 7, 20, 9, 0));
        request.setRecruitEndDate(LocalDateTime.of(2024, 7, 31, 18, 0));
        request.setImageUrl("https://s3-url/image.jpg");
        request.setLatitude("37.4979");
        request.setLongitude("127.0276");

        int result = mapper.insertVolunteer(request);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("봉사 공고 수정 매퍼 테스트")
    public void testUpdateVolunteer() {
        // given
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setVolunteerId(11L); // Assuming this ID exists
        dto.setTitle("수정된 봉사 공고");
        dto.setContent("수정된 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setStartDate(Timestamp.valueOf("2024-08-01 09:00:00").toLocalDateTime());
        dto.setEndDate(Timestamp.valueOf("2024-08-01 12:00:00").toLocalDateTime());
        dto.setAddress("수정된 주소");
        dto.setTotalCount(30);
        dto.setStatus(1);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00").toLocalDateTime());
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59").toLocalDateTime());
        dto.setImageUrl("https://example.com/new_image.jpg");
        dto.setLatitude("37.567");
        dto.setLongitude("126.979");

        // when
        int success = mapper.updateVolunteer(dto);

        // then
        assertEquals(1, success);
    }

    @Test
    @DisplayName("봉사 공고 삭제 매퍼 테스트")
    public void testDeleteVolunteer() {
        // given
        long volunteerId = 11L; // Assuming this ID exists

        // when
        int success = mapper.deleteVolunteer(volunteerId);

        // then
        assertEquals(1, success);
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
