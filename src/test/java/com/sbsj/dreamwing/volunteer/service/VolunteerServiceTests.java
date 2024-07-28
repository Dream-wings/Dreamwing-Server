package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;

import java.util.List;

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
}
