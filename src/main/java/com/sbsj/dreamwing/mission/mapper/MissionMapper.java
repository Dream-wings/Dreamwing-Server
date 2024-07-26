package com.sbsj.dreamwing.mission.mapper;

import com.sbsj.dreamwing.mission.domain.QuizVO;
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
 * </pre>
 */
@Mapper
public interface MissionMapper {

    public QuizVO getQuiz(Long quizId);
}
