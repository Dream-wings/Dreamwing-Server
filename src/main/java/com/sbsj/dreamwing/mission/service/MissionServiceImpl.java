package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.mapper.MissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 미션 서비스 구현체
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
@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService{

    private final MissionMapper mapper;

    @Override
    public QuizVO getRandomQuiz() throws Exception {
        Random rand = new Random();

        long randomLong = rand.nextLong(5);
        log.info(randomLong+"");
        return mapper.getQuiz(randomLong);
    }
}
