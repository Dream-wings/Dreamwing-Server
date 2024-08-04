package com.sbsj.dreamwing.mission.mapper;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.dto.CheckDailyMissionRequestDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 미션 매퍼 인터페이스
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * 2024.07.28   정은지        포인트 부여 메서드 추가
 * 2024.08.04   정은지        데일리 미션 중복 확인 메서드 추가
 * </pre>
 */
@Mapper
public interface MissionMapper {

    QuizVO selectQuiz();

    void callAwardPointsProcedure(AwardPointsRequestDTO dto);

    int selectDailyMissionHistory(CheckDailyMissionRequestDTO dto);
}