package com.sbsj.dreamwing.mission.mapper;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 미션 매퍼 테스트 클래스
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * </pre>
 */

@Slf4j
@SpringBootTest
public class MissionMapperTests {

    @Autowired
    private MissionMapper mapper;

    @Test
    public void testGetQuiz() {

        // given
        Long quizId = 1L;

        // when
        QuizVO quiz = mapper.getQuiz(quizId);

        // then
        log.info(String.valueOf(quiz));
        assertThat(quiz).isNotNull();
    }

    @Test
    public void testCallAwardPointsProcedure() {

        // given
        AwardPointsRequestDTO dto = new AwardPointsRequestDTO();
        dto.setUserId(1L);
        dto.setActivityType(4);
        dto.setActivityTitle("데일리 퀴즈");
        dto.setPoint(30);

        // when
        mapper.callAwardPointsProcedure(dto);

        // then

    }
}
