package com.sbsj.dreamwing.mission.mapper;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
 * 2024.07.28   정은지        포인트 부여 기능 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class MissionMapperTests {

    @Autowired
    private MissionMapper mapper;

    @Test
    @DisplayName("퀴즈 조회 매퍼 테스트")
    public void testGetDailyQuiz() {

        // given
        LocalDate currentDate = LocalDate.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // when
        QuizVO quiz = mapper.selectQuiz();

        // then
        log.info(String.valueOf(quiz));
        log.info(dateFormat.format(quiz.getQuizDate()));
        log.info(currentDate.toString());
        assertThat(dateFormat.format(quiz.getQuizDate())).isEqualTo(currentDate.toString());
    }

    @Test
    @DisplayName("포인트 부여 매퍼 테스트")
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
