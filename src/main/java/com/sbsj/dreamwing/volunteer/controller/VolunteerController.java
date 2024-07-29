package com.sbsj.dreamwing.volunteer.controller;

import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.util.ApiResponse;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import com.sbsj.dreamwing.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VolunteerListDTO>>> getVolunteerList() throws Exception {
        // long 타입은 기본 타입이므로 null 체크가 필요 없음
        // 따라서 null 체크를 제거했습니다.
        List<VolunteerListDTO> response = volunteerService.getVolunteerList();

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, response));
    }


    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<VolunteerDetailDTO>> getVolunteerDetail(@RequestParam long volunteerId) throws Exception {
        // Service 호출 시 인스턴스를 통해 호출
        VolunteerDetailDTO volunteerDetailDTO = volunteerService.getVolunteerDetail(volunteerId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, volunteerDetailDTO));
    }

}