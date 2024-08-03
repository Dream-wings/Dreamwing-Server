package com.sbsj.dreamwing.support.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.support.service.SupportService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 추가
 * 2024.07.28   임재성        모든 후원 리스트 조회 추가
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

    /**
     * 후원 리스트 조회
     * @return
     * @throws Exception
     */
    @GetMapping("/list/5")
    public ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>> getSupportList() throws Exception {
        List<GetSupportListResponseDTO> response = service.getSupportList();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 모든 후원 리스트 조회
     * @return
     * @throws Exception
     */


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>> getAllSupportList() throws Exception {
        List<GetSupportListResponseDTO> response = service.getAllSupportList();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 포인트 기부
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/give")
    public ResponseEntity<ApiResponse<Void>> SupportGivePoints(@RequestBody PostSupportGiveRequestDTO request) throws Exception {
        boolean success = service.SupportGivePoints(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."));
        }
    }



}
