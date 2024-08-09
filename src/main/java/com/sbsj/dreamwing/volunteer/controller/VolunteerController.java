package com.sbsj.dreamwing.volunteer.controller;

import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.user.dto.UserDTO;
import com.sbsj.dreamwing.util.ApiResponse;
import com.sbsj.dreamwing.volunteer.dto.CertificationVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import com.sbsj.dreamwing.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 봉사 컨트롤러
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * 2024.07.28   임재성        봉사 모집공고 게시판 리스트 & 상세 페이지 조회 기능 추가
 * 2024.07.31   임재성        봉사 & 멘토링 리스트 페이징 처리 기능 추가
 * 2024.08.03   정은지        봉사활동 인증 기능 추가
 * 2024.08.03   임재성        봉사활동 신청 상태 확인 기능 추가
 * 2024.08.04   임재성        봉사활동 필터 기능 추가
 * 2024.08.06   임재성        봉사활동 신청 승인 상태 확인 기능 추가
 * 2024.08.06   임재성        JWT 토큰 이용 기능 추가
 * </pre>
 * @since 2024.07.26
 */


@RestController
@RequestMapping(value = "/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    /**
     * 봉사 리스트 조회 API
     * @author 임재성
     * @param page
     * @param size
     * @param status
     * @param type
     * @return ResponseEntity<ApiResponse<List<VolunteerListDTO>>>
     * @throws Exception
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VolunteerListDTO>>> getVolunteerListWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam int status, // Required parameter for status
            @RequestParam(required = false) Integer type // Optional parameter for type
    ) throws Exception {
        List<VolunteerListDTO> response = volunteerService.getVolunteerListWithFilters(page, size, status, type);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 봉사 상세 페이지 조회 API
     * @author 임재성
     * @param volunteerId
     * @return ResponseEntity<ApiResponse<VolunteerDetailDTO>>
     * @throws Exception
     */
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<VolunteerDetailDTO>> getVolunteerDetail(@RequestParam long volunteerId) throws Exception {
        // Service 호출 시 인스턴스를 통해 호출
        VolunteerDetailDTO volunteerDetailDTO = volunteerService.getVolunteerDetail(volunteerId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, volunteerDetailDTO));
    }


    /**
     * 봉사 신청 여부 확인 API (GET 요청)
     * @author 임재성
     * @param volunteerId
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @GetMapping("/check-application")
    public ResponseEntity<ApiResponse<Boolean>> checkApplicationStatus(@RequestParam long volunteerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다. userId를 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        boolean hasApplied = volunteerService.hasUserApplied(volunteerId, userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, hasApplied));
    }


    /**
     * 봉사활동 신청 여부 확인 API (POST 요청)
     * @author 임재성
     * @param request
     * @return ResponseEntity<ApiResponse<Boolean>>
     */
    @PostMapping("/check-application")
    public ResponseEntity<ApiResponse<Boolean>> checkIfAlreadyApplied(@RequestBody PostApplyVolunteerRequestDTO request) {
        boolean hasApplied = volunteerService.hasUserApplied(request.getVolunteerId(), request.getUserId());
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, hasApplied));
    }


    /**
     * 봉사활동 신청 API
     * @author 임재성
     * @param request
     * @return ResponseEntity<ApiResponse<Void>>
     * @throws Exception
     */
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Void>> applyVolunteer(@RequestBody PostApplyVolunteerRequestDTO request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다. userId를 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();
        request.setUserId(userId);
        boolean success = volunteerService.applyVolunteer(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "신청에 실패했습니다."));
        }
    }

    /**
     * 봉사활동 신청 취소 API
     * @author 임재성
     * @param request
     * @return ResponseEntity<ApiResponse<Void>>
     * @throws Exception
     */
    @PostMapping("/cancel-application")
    public ResponseEntity<ApiResponse<Void>> cancelVolunteer(@RequestBody PostApplyVolunteerRequestDTO request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다. userId를 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();
        request.setUserId(userId);
        boolean success = volunteerService.cancelApplication(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "신청 취소에 실패했습니다."));
        }
    }

    /**
     * 봉사활동 인증 API
     * @author 정은지
     * @param request
     * @param imageFile
     * @return ResponseEntity<ApiResponse<Void>>
     * @throws Exception
     */
    @PatchMapping(value = "/certification", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> certificationVolunteer(@RequestPart CertificationVolunteerRequestDTO request,
                                                                    @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws Exception {
        return volunteerService.certificationVolunteer(request, imageFile) ?
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK)) :
                ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사활동 인증 실패"));
    }

    /**
     * 봉사활동 신청 승인 상태 확인 API
     * @author 임재성
     * @param volunteerId
     * @return ResponseEntity<ApiResponse<Integer>>
     */
    @GetMapping("/check-status")
    public ResponseEntity<ApiResponse<Integer>> getApplicationStatus(@RequestParam long volunteerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다. userId를 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();
        int status = volunteerService.getApplicationStatus(volunteerId, userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, status));
    }

}