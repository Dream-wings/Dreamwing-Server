package com.sbsj.dreamwing.admin.controller;

import com.sbsj.dreamwing.admin.dto.AwardVolunteerPointsRequestDTO;
import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.admin.service.AdminService;
import com.sbsj.dreamwing.mission.dto.AwardPointsRequestDTO;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 컨트롤러
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28   정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 승인 기능 추가
 * 2024.07.29   정은지        봉사활동 인증 후 포인트 부여 기능 추가
 * </pre>
 */
@RestController
@RequestMapping(value="/admin", produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final AdminService service;
    private final MissionService missionService;

    /**
     * 사용자 봉사활동 신청 승인
     * @param request
     * @return
     * @throws Exception
     */
    @PatchMapping("/volunteer/approve")
    public ResponseEntity<ApiResponse<Void>> approveVolunteerRequest(
            @RequestBody UpdateVolunteerStatusRequestDTO request) throws Exception {
        return service.approveVolunteerRequest(request) ?
                    ResponseEntity.ok(ApiResponse.success(HttpStatus.OK)) :
                    ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사활동 요청 승인 실패"));
    }

    /**
     * 봉사활동 포인트 부여
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/volunteer/point")
    public ResponseEntity<ApiResponse<Void>> awardVolunteerPoints(
            @RequestBody AwardVolunteerPointsRequestDTO request) throws Exception {
        return service.awardVolunteerPoints(request) ?
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK)) :
                ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사활동 인증 포인트 부여 실패"));
    }
}
