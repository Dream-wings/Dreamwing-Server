package com.sbsj.dreamwing.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 공통 응답 포맷 클래스
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28   정은지        최초 생성
 * </pre>
 */
@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private final int code;
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> createResponse(HttpStatus status, boolean success, String message, T data) {
        return ApiResponse.<T>builder()
                .code(status.value())
                .success(success)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(HttpStatus status, T data) {
        return createResponse(status, true, "요청에 성공하였습니다.", data);
    }

    public static ApiResponse<Void> success(HttpStatus status) {
        return createResponse(status, true, "요청에 성공하였습니다.", null);
    }

    public static ApiResponse<Void> failure(HttpStatus status, String message) {
        return createResponse(status, false, message, null);
    }


    }




