package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
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
 * 2024.07.28   정은지        데일리 퀴즈 정답 시 포인트 부여 기능 추가, 퀴즈 조회 방식 변경
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService{

    private final MissionMapper mapper;

    @Override
    public QuizVO getDailyQuiz() throws Exception {
        return mapper.selectQuiz();
    }

    @Override
    public boolean awardDailyQuizPoints(AwardPointsRequestDTO dto) throws Exception {
        try {
            mapper.callAwardPointsProcedure(dto);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
