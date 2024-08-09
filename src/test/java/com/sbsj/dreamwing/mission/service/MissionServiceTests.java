package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
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
 * 2024.07.28   정은지        포인트 지급 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class MissionServiceTests {

    @Autowired
    private MissionService service;

    @Test
    @DisplayName("퀴즈 조회 서비스 테스트")
    public void testGetRandomQuiz() throws Exception {
        // given
        LocalDate currentDate = LocalDate.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // when
        QuizVO quiz = service.getDailyQuiz();

        // then
        log.info(String.valueOf(quiz));
        log.info(dateFormat.format(quiz.getQuizDate()));
        log.info(currentDate.toString());
        assertThat(dateFormat.format(quiz.getQuizDate())).isEqualTo(currentDate.toString());
    }

    @Test
    @DisplayName("포인트 부여 서비스 테스트")
    public void testAwardDailyMissionPoints() throws Exception {
        // given
        AwardPointsRequestDTO dto = AwardPointsRequestDTO.builder()
                        .userId(1L)
                        .activityType(3)
                        .activityTitle("테스트")
                        .point(30).build();

        // when
        int result = service.awardDailyMissionPoints(dto);

        // then
        assertThat(result).isEqualTo(1);
    }
}
