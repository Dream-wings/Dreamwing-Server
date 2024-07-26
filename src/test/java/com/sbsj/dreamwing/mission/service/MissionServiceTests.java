package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
 * </pre>
 */

@Slf4j
@SpringBootTest
public class MissionServiceTests {

    @Autowired
    private MissionService service;

    @Test
    public void testGetRandomQuiz() {
        try {
            QuizVO quiz = service.getRandomQuiz();
            log.info("test");
            log.info(String.valueOf(quiz));
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }
}
