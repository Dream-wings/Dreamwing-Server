package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 미션 서비스 테스트
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * 2024.07.28   정은지        포인트 부여 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class MissionServiceTests {

    @Autowired
    private MissionService service;

    @Test
    @DisplayName("퀴즈 조회 테스트")
    public void testGetRandomQuiz() throws Exception {
        QuizVO quiz = service.getRandomQuiz();
        log.info(String.valueOf(quiz));
    }

    @Test
    @DisplayName("포인트 부여 테스트")
    public void testAwardDailyQuizPoints() throws Exception {
        // given
        AwardPointsRequestDTO dto = new AwardPointsRequestDTO();
        dto.setUserId(1L);
        dto.setActivityType(3);
        dto.setActivityTitle("테스트");
        dto.setPoint(300);

        // when
        boolean success = service.awardDailyQuizPoints(dto);

        // then
        assertTrue(success, "포인트 부여 성공");
    }
}
