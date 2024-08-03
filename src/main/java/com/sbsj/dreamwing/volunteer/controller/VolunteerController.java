package com.sbsj.dreamwing.volunteer.controller;

import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
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
 * </pre>
 */



@RestController
@RequestMapping(value="/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;


//    @GetMapping("/list")
//    public ResponseEntity<ApiResponse<List<VolunteerListDTO>>> getVolunteerList() throws Exception {
//        // long 타입은 기본 타입이므로 null 체크가 필요 없음
//        // 따라서 null 체크를 제거했습니다.
//        List<VolunteerListDTO> response = volunteerService.getVolunteerList();
//
//        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
//    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VolunteerListDTO>>> getVolunteerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) throws Exception {
        List<VolunteerListDTO> response = volunteerService.getVolunteerList(page, size);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }



    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<VolunteerDetailDTO>> getVolunteerDetail(@RequestParam long volunteerId) throws Exception {
        // Service 호출 시 인스턴스를 통해 호출
        VolunteerDetailDTO volunteerDetailDTO = volunteerService.getVolunteerDetail(volunteerId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, volunteerDetailDTO));
    }
    // New endpoint to check if a user has applied
    @GetMapping("/check-application")
    public ResponseEntity<ApiResponse<Boolean>> checkApplicationStatus(@RequestParam long volunteerId, @RequestParam long userId) {
        boolean hasApplied = volunteerService.hasUserApplied(volunteerId, userId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, hasApplied));
    }

    @PostMapping("/check-application")
    public ResponseEntity<ApiResponse<Boolean>> checkIfAlreadyApplied(@RequestBody PostApplyVolunteerRequestDTO request) {
        boolean hasApplied = volunteerService.hasUserApplied(request.getVolunteerId(), request.getUserId());
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, hasApplied));
    }
//    @PostMapping("/cancel-application")
//    public ResponseEntity<ApiResponse<Void>> cancelApplication(@RequestBody PostApplyVolunteerRequestDTO request) {
//        try {
//            volunteerService.cancelApplication(request);
//            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "Cancellation failed"));
//        }
//    }




    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Void>> applyVolunteer(@RequestBody PostApplyVolunteerRequestDTO request) throws Exception {
        boolean success = volunteerService.applyVolunteer(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "신청에 실패했습니다."));
        }
    }
    @PostMapping("/cancel-application")
    public ResponseEntity<ApiResponse<Void>> cancelVolunteer(@RequestBody PostApplyVolunteerRequestDTO request) throws Exception {
        boolean success = volunteerService.cancelApplication(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "신청 취소에 실패했습니다."));
        }
    }

    /**
     * 봉사활동 인증 테스트
     *
     * @param request
     * @param imageFile
     * @return
     * @throws Exception
     */
    @PatchMapping(value = "/certification", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> certificationVolunteer(@RequestPart CertificationVolunteerRequestDTO request,
                                                                    @RequestPart(value = "imageUrl", required = false) MultipartFile imageFile) throws Exception {
        return volunteerService.certificationVolunteer(request, imageFile) ?
                ResponseEntity.ok(ApiResponse.success(HttpStatus.OK)) :
                ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "봉사활동 인증 실패"));
    }

}