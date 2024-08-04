package com.sbsj.dreamwing.user.controller;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.dto.*;
import com.sbsj.dreamwing.user.service.UserService;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 사용자 관련 요청을 처리하는 Controller 클래스
 * @apiNote 회원가입, 로그인 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.28     	정은찬        		        최초 생성 및 회원가입 API 작성
 *  2024.07.30      정은찬                       로그인 기능 API 추가
 *  2024.07.31      정은찬                       회원탈퇴 기능 API 및 회원 정보 가져오기 API 추가
 *  2024.07.31      정은찬                       회원 정보 업데이트 API 및 로그아웃 API 추가
 *  2024.07.31      정은찬                       포인트 내역 조회 API 및 후원 내역 조회 API 추가
 *  2024.08.02      정은찬                       로그인 아이디 존재 여부 확인 API 추가
 *  2024.08.04      정은찬                       마이페이지 사용자 정보 API 추가
 *  2024.08.04      정은찬                       페이징 처리를 위해 포인트 내역 조회 API 수정
 *
 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signUp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> signUp(@ModelAttribute SignUpRequestDTO signUpRequestDTO) throws Exception {

        String result = userService.signUp(signUpRequestDTO);

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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequestDTO LoginRequestDTO) {
        try {
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
     *
     * @return
     */
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw() {
        // SecurityContext에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        boolean result = userService.withdraw(userId);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "회원탈퇴 실패"));
        }
    }

    @GetMapping("/userInfo")
    public ResponseEntity<ApiResponse<UserInfoDTO>> getUserInfo() throws Exception {
        // SecurityContext에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, userInfoDTO));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> update(@ModelAttribute UserUpdateDTO userUpdateDTO) throws Exception {
        // SecurityContext에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        Boolean result = userService.updateUserInfo(userId, userUpdateDTO);

        if (result) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "업데이트 실패"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        try {
            // SecurityContext에서 Authentication 객체를 가져옵니다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // UserDetails 객체에서 userId를 가져옵니다.
            // 여기서는 UserDetails의 `getUsername` 메서드를 사용한다고 가정합니다.
            long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

            userService.logout(userId);

            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "로그아웃 실패"));
        }
    }

    @GetMapping("/getPointList")
    public ResponseEntity<ApiResponse<List<MyPointVO>>> getUserPointList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // SecurityContext에서 인증 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체에서 사용자 ID를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        // 페이징 정보를 설정합니다.
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 포인트 리스트를 가져옵니다.
        List<MyPointVO> pointList = userService.getUserPointList(userId, page, size);

        // 응답 엔티티를 반환합니다.
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, pointList));
    }


    @GetMapping("/getSupportList")
    public ResponseEntity<ApiResponse<List<MySupportVO>>> getUserSupportList() {
        // SecurityContext에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        List<MySupportVO> supportList = userService.getUserSupportList(userId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, supportList));
    }

    @GetMapping("/checkExistId")
    public ResponseEntity<ApiResponse<Boolean>> checkExistId(String loginId) {
        LoginIdDTO loginIdDTO = new LoginIdDTO();
        loginIdDTO.setLoginId(loginId);
        boolean result = userService.checkExistLoginId(loginIdDTO);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, result));
    }
    @GetMapping("/getMyPageInfo")
    public ResponseEntity<ApiResponse<MyPageDTO>> getMyPageInfo() {
        // SecurityContext에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserDetails 객체에서 userId를 가져옵니다.
        long userId = ((UserDTO) authentication.getPrincipal()).getUserId();

        MyPageDTO myPageDTO = userService.getMyPageInfo(userId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, myPageDTO));
    }

}
