package com.sbsj.dreamwing.support.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.service.SupportService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 후원 컨트롤러
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28   정은지        최초 생성
 * </pre>
 */

@RestController
@RequestMapping(value="/support", produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class SupportController {

    private final SupportService service;

    /**
     * 후원 총 횟수 및 금액 조회
     * @return
     * @throws Exception
     */
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<GetTotalSupportResponseDTO>> getTotalSupport() throws Exception {
        GetTotalSupportResponseDTO response = service.getTotalSupport();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }
}
