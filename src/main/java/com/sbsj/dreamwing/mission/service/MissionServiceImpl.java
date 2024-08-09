package com.sbsj.dreamwing.mission.service;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.dto.CheckDailyMissionRequestDTO;
import com.sbsj.dreamwing.mission.mapper.MissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sbsj.dreamwing.user.dto.UserDTO;

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
 * 2024.07.28   정은지        포인트 지급 메서드 추가, 퀴즈 조회 방식 변경
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService{

    private final MissionMapper mapper;

    /**
     * 데일리 퀴즈 조회
     * @author 정은지
     * @return QuizVO
     * @throws Exception
     */
    @Override
    public QuizVO getDailyQuiz() throws Exception {
        return mapper.selectQuiz();
    }

    /**
     * 포인트 지급 처리
     * @author 정은지
     * @param awardPointsRequestDTO
     * @return int
     * @throws Exception
     */
    @Transactional
    @Override
    public int awardDailyMissionPoints(AwardPointsRequestDTO awardPointsRequestDTO) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

            awardPointsRequestDTO.setUserId(userId);

            CheckDailyMissionRequestDTO requestDTO = CheckDailyMissionRequestDTO.builder()
                    .userId(userId)
                    .activityType(awardPointsRequestDTO.getActivityType())
                    .activityTitle(awardPointsRequestDTO.getActivityTitle())
                    .build();

            log.info("CheckDailyMissionRequestDTO: " + requestDTO.getUserId() + " " + requestDTO.getActivityType() + " " + requestDTO.getActivityTitle());

            if (mapper.selectDailyMissionHistory(requestDTO) == 0) {
                mapper.callAwardPointsProcedure(awardPointsRequestDTO);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
