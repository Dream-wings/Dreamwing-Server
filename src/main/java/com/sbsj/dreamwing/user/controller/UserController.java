package com.sbsj.dreamwing.user.controller;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.MyVolunteerVO;
import com.sbsj.dreamwing.user.dto.*;
import com.sbsj.dreamwing.user.service.UserService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회원 관련 요청을 처리하는 Controller 클래스
 * @apiNote 회원가입, 로그인 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        	수정자       				    수정내용
 * ----------  ----------------    --------------------------------------------------------------------------
 *  2024.07.28     	정은찬        		        최초 생성 및 회원가입 API 작성
 *  2024.07.30      정은찬                       로그인 기능 API 추가
 *  2024.07.31      정은찬                       회원탈퇴 기능 API 및 회원 정보 가져오기 API 추가
 *  2024.07.31      정은찬                       회원 정보 업데이트 API 및 로그아웃 API 추가
 *  2024.07.31      정은찬                       회원 포인트 내역 조회 API 및 후원 내역 조회 API 추가
 *  2024.08.02      정은찬                       로그인 아이디 존재 여부 확인 API 추가
 *  2024.08.04      정은찬                       마이페이지 회원 정보 API 추가
 *  2024.08.04      정은찬                       페이징 처리를 위해 회원 포인트 내역 조회, 후원 내역 조회 API 수정
 *  2024.08.05      정은찬                       회원 정보 업데이트 API 수정, 회원 봉사 활동 내역 조회 API 추가

 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 API
     * @author 정은찬
     * @param signUpRequestDTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping(value = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> signUp(@ModelAttribute SignUpRequestDTO signUpRequestDTO) {
        // 회원가입 서비스 호출
        String result = userService.signUp(signUpRequestDTO);

        // 회원가입 결과에 따라 응답을 반환
        if (result.equals("사용자 등록 성공")) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else if (result.equals("중복 아이디 존재")) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "회원가입 실패 - 중복 아이디가 존재합니다."));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "회원가입 실패"));
        }
    }

    /**
     * 로그인 API
     * @author 정은찬
     * @param LoginRequestDTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequestDTO LoginRequestDTO) {
        try {
            // 로그인 서비스 호출 및 JWT 토큰 생성
            String token = userService.login(LoginRequestDTO);

            // HttpHeaders 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            // ResponseEntity 반환
            return ResponseEntity.ok().headers(headers).body(ApiResponse.success(HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    /**
     * 회원탈퇴 API
     * @author 정은찬
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw() {
        // SecurityContext에서 Authentication 객체 가져움
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 회원탈퇴 서비스 호출
        boolean result = userService.withdraw(userId);

        // 회원탈퇴 결과에 따라 적절한 응답을 반환
        if (result) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "회원탈퇴 실패"));
        }
    }

    /**
     * 회원 정보 조회 API
     * @author 정은찬
     * @return ResponseEntity<ApiResponse<UserInfoDTO>>
     * @throws Exception
     */
    @GetMapping("/userInfo")
    public ResponseEntity<ApiResponse<UserInfoDTO>> getUserInfo() throws Exception {
        // SecurityContext에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 사용자 정보 조회 서비스 호출
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, userInfoDTO));
    }

    /**
     * 회원 정보 업데이트 API
     * @author 정은찬
     * @param userUpdateDTO
     * @return  ResponseEntity<ApiResponse<Void>>
     * @throws Exception
     */
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> update(@ModelAttribute UserUpdateDTO userUpdateDTO) throws Exception {
        // SecurityContext에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 사용자 정보 업데이트 서비스 호출
        Boolean result = userService.updateUserInfo(userId, userUpdateDTO);

        // 사용자 정보 업데이트 결과에 따라 적절한 응답을 반환
        if (result) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "업데이트 실패"));
        }
    }

    /**
     * 로그아웃 API
     * @author 정은찬
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        try {
            // SecurityContext에서 Authentication 객체 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // UserDetails 객체에서 userId 가져옴
            long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

            // 로그아웃 서비스 호출
            userService.logout(userId);

            // 로그아웃 처리 성공 시, 성공 응답 반환
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } catch (Exception e) {
            // 로그아웃 처리 중 예외가 발생한 경우
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "로그아웃 실패"));
        }
    }

    /**
     * 회원 포인트 내역 조회 API
     * @author 정은찬
     * @param page
     * @param size
     * @return ResponseEntity<ApiResponse<List<MyPointVO>>>
     */
    @GetMapping("/getPointList")
    public ResponseEntity<ApiResponse<List<MyPointVO>>> getUserPointList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {

        // SecurityContext에서 인증 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 회원 포인트 내역 조회 서비스 호출
        List<MyPointVO> pointList = userService.getUserPointList(userId, page, size);

        // 포인트 내역 리스트를 포함한 응답 반환
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, pointList));
    }

    /**
     * 회원 후원 내역 조회 API
     * @author 정은찬
     * @param page
     * @param size
     * @return ResponseEntity<ApiResponse<List<MySupportVO>>>
     */
    @GetMapping("/getSupportList")
    public ResponseEntity<ApiResponse<List<MySupportVO>>> getUserSupportList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // SecurityContext에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();
        
        // 회원 후원 내역 조회 서비스 호출
        List<MySupportVO> supportList = userService.getUserSupportList(userId, page, size);

        // 후원 내역 리스트를 포함한 응답 반환
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, supportList));
    }

    /**
     * 회원 봉사 활동 내역 조회 API
     * @author 정은찬
     * @param page
     * @param size
     * @return ResponseEntity<ApiResponse<List<MyVolunteerVO>>>
     */
    @GetMapping("/getVolunteerList")
    public ResponseEntity<ApiResponse<List<MyVolunteerVO>>> getVolunteerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        // SecurityContext에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 회원 봉사 활동 내역 조회 서비스 호출
        List<MyVolunteerVO> volunteerList = userService.getUserVolunteerList(userId, page, size);

        // 봉사 활동 내역 리스트를 포함한 응답 반환
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, volunteerList));
    }

    /**
     * 로그인 아이디 존재 여부 확인 API
     * @author 정은찬
     * @param loginId
     * @return  ResponseEntity<ApiResponse<Boolean>>
     */
    @GetMapping("/checkExistId")
    public ResponseEntity<ApiResponse<Boolean>> checkExistId(String loginId) {

        // 로그인 ID를 포함하는 DTO 객체 생성
        LoginIdDTO loginIdDTO = new LoginIdDTO();
        loginIdDTO.setLoginId(loginId);

        // 로그인 ID 존재 여부 확인 서비스 호출
        boolean result = userService.checkExistLoginId(loginIdDTO);

        // 확인 결과를 포함한 응답 반환
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, result));
    }

    /**
     * 마이페이지 회원 정보 조회 API
     * @author 정은찬
     * @return ResponseEntity<ApiResponse<MyPageDTO>>
     */
    @GetMapping("/getMyPageInfo")
    public ResponseEntity<ApiResponse<MyPageDTO>> getMyPageInfo() {
        // SecurityContext에서 Authentication 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId 가져옴
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 마이페이지 정보 조회 서비스 호출
        MyPageDTO myPageDTO = userService.getMyPageInfo(userId);

        // 조회된 마이 페이지 정보를 포함한 응답을 반환
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, myPageDTO));
    }
}
