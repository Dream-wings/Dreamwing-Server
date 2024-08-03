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
 * @author
 * @since
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.03   정은지       봉사활동 인증 매퍼 테스트 추가
 * </pre>
 */
@Slf4j
@SpringBootTest
public class VolunteerMapperTests {

    @Autowired
    private VolunteerMapper mapper;

    @Test
    public void testGetVolunteerList() {

        int offset = 0;
        int size =2;
        int status=1;
        // when
        List<VolunteerListDTO> volunteerDTO = mapper.getVolunteerList(offset,size,status);

        // then
        log.info(String.valueOf(volunteerDTO));
        assertThat(volunteerDTO).isNotNull();
    }

    @Test
    public void testGetVolunteerDetail() {


        long volunteerId =5L;
        // when
        VolunteerDetailDTO volunteerDetailDTO = mapper.getVolunteerDetail(volunteerId);

        // then
        log.info(String.valueOf(volunteerDetailDTO));
        assertThat(volunteerDetailDTO).isNotNull();
    }

    @Test
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
