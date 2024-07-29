package com.sbsj.dreamwing.user.controller;

import com.sbsj.dreamwing.user.service.UserService;
import com.sbsj.dreamwing.user.dto.SignUpRequestDTO;
import com.sbsj.dreamwing.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 관련 요청을 처리하는 Controller 클래스
 * @apiNote 회원가입, 로그인 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.28     	정은찬        		        최초 생성
 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        boolean success = userService.signUp(signUpRequestDTO);

        if (success) {
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK));
        }
        else {
            return ResponseEntity.badRequest().body(ApiResponse.failure(HttpStatus.BAD_REQUEST, "회원가입 실패"));
        }
    }
}
