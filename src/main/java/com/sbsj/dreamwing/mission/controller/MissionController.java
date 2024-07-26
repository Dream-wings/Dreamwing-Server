package com.sbsj.dreamwing.mission.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.service.MissionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * </pre>
 */

@RestController
@RequestMapping(value="/mission", produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MissionController {

    private final MissionService service;

    @GetMapping("/quiz")
    public ResponseEntity<QuizVO> GetDailyQuiz() throws Exception {
        QuizVO response = service.getRandomQuiz();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
