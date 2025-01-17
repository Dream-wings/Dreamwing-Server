package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.dto.CheckDailyMissionRequestDTO;

/**
 * 미션 서비스 인터페이스
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * 2024.07.28   정은지        포인트 지급 메서드 추가
 * </pre>
 */
public interface MissionService {

    /**
     * 데일리 퀴즈 조회
     * @author 정은지
     * @return QuizVO
     * @throws Exception
     */
    QuizVO getDailyQuiz() throws Exception;

    /**
     * 포인트 지급 처리
     * @author 정은지
     * @param awardPointsRequestDTO
     * @return int
     * @throws Exception
     */
    int awardDailyMissionPoints(AwardPointsRequestDTO awardPointsRequestDTO) throws Exception;
}
