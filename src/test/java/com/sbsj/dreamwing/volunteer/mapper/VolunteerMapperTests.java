package com.sbsj.dreamwing.volunteer.mapper;

import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 봉사 매퍼 테스트 클래스
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26   임재성        최초생성
 * 2024.07.28   임재성        봉사 모집공고 게시판 리스트 & 상세 페이지 조회 기능 매퍼 테스트 추가
 * 2024.07.31   임재성        봉사 & 멘토링 리스트 페이징 처리 기능 매퍼 테스트 추가
 * 2024.08.03   정은지        봉사활동 인증 매퍼 테스트 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 기능 매퍼 테스트 추가
 * 2024.08.04   임재성        봉사활동 필터 기능 매퍼 테스트 추가
 * * </pre>
 */
@Slf4j
@SpringBootTest
public class VolunteerMapperTests {

    @Autowired
    private VolunteerMapper mapper;


    @Test
    @DisplayName("봉사 모집공고 게시판 리스트 조회 테스트")
    public void testGetVolunteerListWithFilters() {

        int offset = 0;
        int size =2;
        int status=1;
        Integer type = 0;
        // when
        List<VolunteerListDTO> volunteerDTO = mapper.getVolunteerListWithFilters(offset,size,status,type);

        // then
        log.info(String.valueOf(volunteerDTO));
        assertThat(volunteerDTO).isNotNull();
    }

    @Test
    @DisplayName("봉사 모집공고 상세 페이지 조회 테스트")
    public void testGetVolunteerDetail() {

        long volunteerId =5L;
        // when
        VolunteerDetailDTO volunteerDetailDTO = mapper.getVolunteerDetail(volunteerId);

        // then
        log.info(String.valueOf(volunteerDetailDTO));
        assertThat(volunteerDetailDTO).isNotNull();
    }

    @Test
    @DisplayName("봉사 신청 테스트")
    public void testInsertVolunteerApplication() {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(1L);
        request.setUserId(2L);

        // when
        int result = mapper.insertVolunteerApplication(request);

        // then
        assertThat(result).isEqualTo(1); // Assuming 1 means success
    }

    @Test
    @DisplayName("봉사 신청 상태 여부 테스트")
    public void testCheckIfAlreadyApplied() {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(3L);
        request.setUserId(2L);

        // when
        int count = mapper.checkIfAlreadyApplied(request);

        // then
        assertThat(count).isGreaterThanOrEqualTo(0); // Expecting 0 or 1
    }

    @Test
    @DisplayName("봉사 신청 취소 테스트")
    public void testDeleteVolunteerApplication() {
        PostApplyVolunteerRequestDTO request = new PostApplyVolunteerRequestDTO();
        request.setVolunteerId(1L);
        request.setUserId(2L);

        // Pre-condition: Insert the record to ensure it exists
        mapper.insertVolunteerApplication(request);

        // when
        int result = mapper.deleteVolunteerApplication(request);

        // then
        assertThat(result).isEqualTo(1); // Assuming 1 means success
    }

    @Test
    @DisplayName("봉사활동 이미지 업데이트 테스트")
    public void testUpdateImageUserVolunteer() {

        // given
        CertificationVolunteerRequestDTO dto = CertificationVolunteerRequestDTO.builder()
                .userId(1)
                .volunteerId(1)
                .imageUrl("http://example.url")
                .build();

        // when
        int success = mapper.updateImageUserVolunteer(dto);

        // then
        assertThat(success).isEqualTo(1);
    }

}
