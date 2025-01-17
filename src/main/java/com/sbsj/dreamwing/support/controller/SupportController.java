package com.sbsj.dreamwing.support.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.support.dto.GetDetailSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.support.service.SupportService;
import com.sbsj.dreamwing.user.dto.UserDTO;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 메서드 추가
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
     * 후원 총 횟수 및 금액 조회 API
     * @author 정은지
     * @return ResponseEntity<ApiResponse<GetTotalSupportResponseDTO>>
     * @throws Exception
     */
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<GetTotalSupportResponseDTO>> getTotalSupport() throws Exception {
        GetTotalSupportResponseDTO response = service.getTotalSupport();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 후원 리스트 조회 API
     * @author 정은지
     * @return ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>>
     * @throws Exception
     */
    @GetMapping("/list/5")
    public ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>> getSupportList() throws Exception {
        List<GetSupportListResponseDTO> response = service.getSupportList();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 모든 후원 리스트 조회
     * @author 임재성
     * @return ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>>
     * @throws Exception
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<GetSupportListResponseDTO>>> getSupportListWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size, // Use default size 2
            @RequestParam int status,
            @RequestParam int category
    ) throws Exception {
        List<GetSupportListResponseDTO> response = service.getSupportListWithFilters(page, size, status, category);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 포인트 기부
     * @author 임재성
     * @param supportId
     * @param amount
     * @param title
     * @param type
     * @return ResponseEntity<ApiResponse<Void>>
     * @throws Exception
     */
    @PostMapping("/donate")
    public ResponseEntity<ApiResponse<Void>> supportGivePoints(

            @RequestParam("supportId") long supportId,
            @RequestParam("amount") int amount,
            @RequestParam("activityTitle") String title,
            @RequestParam("activityType") int type) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다. userId를 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        PostSupportGiveRequestDTO request = new PostSupportGiveRequestDTO(supportId, userId, amount, title,type);

        boolean success = service.SupportGivePoints(request);

        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."));
        }
    }


    /**
     * 후원 상세 정보 조회
     * @author 임재성
     * @param supportId
     * @return ResponseEntity<ApiResponse<GetDetailSupportResponseDTO>>
     * @throws Exception
     */
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<GetDetailSupportResponseDTO>> getSupportDetail(@RequestParam long supportId) throws Exception {
        GetDetailSupportResponseDTO response = service.getSupportDetail(supportId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }



}
