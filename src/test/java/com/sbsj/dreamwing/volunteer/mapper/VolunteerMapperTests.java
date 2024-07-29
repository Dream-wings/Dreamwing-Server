package com.sbsj.dreamwing.volunteer.mapper;

import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class VolunteerMapperTests {

    @Autowired
    private VolunteerMapper mapper;

    @Test
    public void testGetVolunteerList() {

        // when
        List<VolunteerListDTO> volunteerDTO = mapper.getVolunteerList();

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
}
