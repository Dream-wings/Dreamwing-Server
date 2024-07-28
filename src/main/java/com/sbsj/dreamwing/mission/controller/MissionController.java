package com.sbsj.dreamwing.mission.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 미션 컨트롤러
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26   정은지        최초 생성
 * 2024.07.27   정은지        데일리 퀴즈 정답 시 포인트 부여 기능 추가
 * </pre>
 */

@RestController
@RequestMapping(value="/mission", produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MissionController {

    private final MissionService service;

    /**
     * 데일리 퀴즈 조회
     * @return
     * @throws Exception
     */
    @GetMapping("/quiz")
    public ResponseEntity<ApiResponse<QuizVO>> getDailyQuiz() throws Exception {
        QuizVO response = service.getRandomQuiz();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 데일리 퀴즈 정답 시 포인트 부여
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping("/point")
    public ResponseEntity<ApiResponse<Void>> awardDailyQuizPoints(@RequestBody AwardPointsRequestDTO dto) throws Exception {
        boolean success = service.awardDailyQuizPoints(dto);

        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "포인트 부여 실패"));
        }
    }
}
