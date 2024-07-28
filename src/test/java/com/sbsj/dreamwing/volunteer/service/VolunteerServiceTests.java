package com.sbsj.dreamwing.volunteer.service;


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
}
