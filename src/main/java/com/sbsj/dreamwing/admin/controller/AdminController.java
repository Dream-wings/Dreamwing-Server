package com.sbsj.dreamwing.admin.controller;

import com.sbsj.dreamwing.admin.dto.*;
import com.sbsj.dreamwing.admin.service.AdminService;
import com.sbsj.dreamwing.mission.service.MissionService;
import com.sbsj.dreamwing.util.ApiResponse;
import com.sbsj.dreamwing.util.S3Uploader;
import com.sbsj.dreamwing.volunteer.service.VolunteerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 조회 기능 추가
 * 2024.07.30   임재성        봉사 & 멘토링 공고 글 생성/수정/삭제 기능 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 목록, 상세 조회 추가
 * 2024.08.05   정은지        봉사활동 인증 대기 목록, 상세 조회 추가
 * </pre>
 */
@RestController
@RequestMapping(value="/admin", produces=MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final AdminService service;
    private final MissionService missionService;
    private final VolunteerService volunteerService;

    //사진업로드 위한 s3
    private final S3Uploader s3Uploader;


    /**
     * 사용자 봉사활동 신청 승인
     * @author 정은지
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
     * @author 정은지
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


    /**
     * 봉사 공고 생성
     * @param request
     * @return
     */
    @PostMapping("/volunteer/create")
    public ResponseEntity<ApiResponse<Void>> createVolunteer(
            @ModelAttribute AdminVolunteerRequestDTO request) {
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String imageUrl = s3Uploader.uploadFile(request.getImageFile());
            request.setImageUrl(imageUrl);
        }

        int result = service.createVolunteer(request);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사 공고 생성에 실패했습니다."));
        }
    }
    // 봉사 공고 상세 조회
    // 봉사 공고 상세 조회
//    @GetMapping("/detail")
//    public ResponseEntity<ApiResponse<AdminVolunteerRequestDTO>> getVolunteerDetail(@RequestParam long volunteerId) throws Exception{
//        // 서비스 호출
//        VolunteerDetailDTO volunteerDetailDTO = volunteerService.getVolunteerDetail(volunteerId);
//        // 상세 조회 결과에 대한 응답 반환
//        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, volunteerDetailDTO));
//
//    }

    /**
     * 봉사 공고 수정
     * @param request
     * @return
     */
    @PutMapping("/volunteer/update")
    public ResponseEntity<ApiResponse<Void>> updateVolunteer(
            @RequestBody AdminVolunteerRequestDTO request) {
        int result = service.updateVolunteer(request);
        if (result > 0) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사 공고 수정에 실패했습니다."));
        }
    }

    /**
     * 봉사 공고 삭제
     * @param volunteerId
     * @return
     */
    @DeleteMapping("/volunteer/{volunteerId}")
    public ResponseEntity<ApiResponse<Void>> deleteVolunteer(
            @PathVariable long volunteerId) {
        int result = service.deleteVolunteer(volunteerId);
        if (result > 0) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사 공고 삭제에 실패했습니다."));
        }
    }

    /**
     * 봉사 공고 목록 조회
     * @return
     */
    @GetMapping("/volunteer/list")
    public ResponseEntity<ApiResponse<List<AdminVolunteerResponseDTO>>> getVolunteerList() {
        List<AdminVolunteerResponseDTO> response = service.getVolunteerList();
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 봉사활동 신청 대기 목록 조회
     * @author 정은지
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping("/volunteer/request/list")
    public ResponseEntity<ApiResponse<List<VolunteerRequestListResponseDTO>>>
                    getVolunteerRequestPendingList(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) throws Exception {
        List<VolunteerRequestListResponseDTO> response =
                service.getVolunteerRequestPendingList(page, size);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 봉사활동 신청 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/volunteer/request")
    public ResponseEntity<ApiResponse<VolunteerRequestDetailResponseDTO>>
                    getVolunteerRequestPendingDetail(@RequestParam long volunteerId, @RequestParam long userId) throws Exception {
        VolunteerRequestDetailResponseDTO response =
                service.getVolunteerRequestPendingDetail(volunteerId, userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 봉사활동 인증 대기 목록 조회
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping("/volunteer/certification/list")
    public ResponseEntity<ApiResponse<List<VolunteerRequestListResponseDTO>>>
    getVolunteerCertificationList(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) throws Exception {
        List<VolunteerRequestListResponseDTO> response =
                service.getVolunteerCertificationList(page, size);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 봉사활동 인증 대기 상세 조회
     * @author 정은지
     * @param volunteerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("/volunteer/certification")
    public ResponseEntity<ApiResponse<VolunteerCertificationDetailResponseDTO>>
    getVolunteerCertificationDetail(@RequestParam long volunteerId, @RequestParam long userId) throws Exception {
        VolunteerCertificationDetailResponseDTO response =
                service.getVolunteerCertificationDetail(volunteerId, userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }


    /**
     * 봉사 공고 목록 조회 (페이징)
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/volunteer/adminList")
    public ResponseEntity<ApiResponse<List<AdminVolunteerResponseDTO>>> getVolunteerListWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<AdminVolunteerResponseDTO> response = service.getVolunteerListWithPaging(page, size);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }
}
